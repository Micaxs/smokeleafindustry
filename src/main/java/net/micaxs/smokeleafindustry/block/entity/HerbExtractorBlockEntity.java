package net.micaxs.smokeleafindustry.block.entity;

import net.micaxs.smokeleafindustry.fluid.ModFluids;
import net.micaxs.smokeleafindustry.recipe.machines.HerbExtractorRecipe;
import net.micaxs.smokeleafindustry.screen.HerbExtractorMenu;
import net.micaxs.smokeleafindustry.utils.HashOilHelper;
import net.micaxs.smokeleafindustry.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HerbExtractorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            List<HerbExtractorRecipe> recipes = level.getRecipeManager().getAllRecipesFor(HerbExtractorRecipe.Type.INSTANCE);
            for (HerbExtractorRecipe recipe : recipes) {
                if (recipe.matches(new SimpleContainer(stack), level)) {
                    return true;
                }
            }
            return false;
        }
    };

    private static final int INPUT_SLOT = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final ModEnergyStorage energy = new ModEnergyStorage(10000, 50, 50, 0);
    private final LazyOptional<ModEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.energy);
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final FluidTank FLUID_TANK = new FluidTank(8000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    public HerbExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HERB_EXTRACTOR_BE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HerbExtractorBlockEntity.this.progress;
                    case 1 -> HerbExtractorBlockEntity.this.maxProgress;
                    case 2 -> HerbExtractorBlockEntity.this.energy.getEnergyStored();
                    case 3 -> HerbExtractorBlockEntity.this.energy.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HerbExtractorBlockEntity.this.progress = pValue;
                    case 1 -> HerbExtractorBlockEntity.this.maxProgress = pValue;
                    case 2 -> HerbExtractorBlockEntity.this.energy.setEnergy(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStack() {
        if (itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()) {
            return itemHandler.getStackInSlot(INPUT_SLOT);
        }
        return null;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        } else if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergy.cast();
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergy.invalidate();
        lazyFluidHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.smokeleafindustry.herb_extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HerbExtractorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("herb_extractor.progress", progress);
        pTag.put("energy", this.energy.serializeNBT());
        pTag = FLUID_TANK.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energy.deserializeNBT(pTag.get("energy"));
        progress = pTag.getInt("herb_extractor.progress");
        FLUID_TANK.readFromNBT(pTag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, HerbExtractorBlockEntity pEntity) {
        boolean hasEnergy = energy.getEnergyStored() > 0;

        if (hasEnergy && hasRecipe()) {
            increaseCraftingProgress();
            // Energy consumption is hardcoded to 2000 for all items, consider moving to json file / recipe
            energy.removeEnergy(20);
            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {
                extractToFluid();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void extractToFluid() {
        // Fluid tank is assumed to have space, checked in hasRecipe method
        Optional<HerbExtractorRecipe> recipe = getCurrentRecipe();
        FluidStack fluidStack = recipe.get().getResultFluid();

        int filledAmount = fill(fluidStack);
        if (filledAmount > 0) {
            ItemStack inputItem = itemHandler.extractItem(INPUT_SLOT, 1, false);
            // Set tanks NBT data to correctly represent the fluid's weed source
            FluidStack hashOil = FLUID_TANK.getFluid();
            if(HashOilHelper.setWeedNBTDataFluid(inputItem, hashOil)) {
                // Convert hash oil to sludge
                FluidStack fluid = new FluidStack(ModFluids.SOURCE_HASH_OIL_SLUDGE.get(), FLUID_TANK.getFluidAmount());
                FLUID_TANK.setFluid(fluid);
            }
        }
    }

    private int fill(FluidStack hashOilFluid) {
        int capacity = FLUID_TANK.getCapacity();
        FluidStack fluid = FLUID_TANK.getFluid();

        if (fluid.isEmpty()) {
            fluid = new FluidStack(hashOilFluid, Math.min(capacity, hashOilFluid.getAmount()));
            FLUID_TANK.setFluid(fluid);
            return fluid.getAmount();
        }

        int filled = capacity - fluid.getAmount();

        if (hashOilFluid.getAmount() < filled) {
            fluid.grow(hashOilFluid.getAmount());
            filled = hashOilFluid.getAmount();
        } else {
            fluid.setAmount(capacity);
        }
        return filled;
    }

    private boolean hasRecipe() {
        Optional<HerbExtractorRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        // Check if tank doesn't have room for the result of the current recipe
        if (FLUID_TANK.getFluidAmount() > FLUID_TANK.getCapacity() - recipe.get().getResultFluid().getAmount()) {
            return false;
        }

        return true;
    }

    private Optional<HerbExtractorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(HerbExtractorRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    public LazyOptional<ModEnergyStorage> getLazyEnergy() {
        return this.lazyEnergy;
    }

    public ModEnergyStorage getEnergy() {
        return this.energy;
    }

    public FluidTank getFluidTank() {
        return this.FLUID_TANK;
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }
}
