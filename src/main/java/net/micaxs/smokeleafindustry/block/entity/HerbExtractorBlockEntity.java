package net.micaxs.smokeleafindustry.block.entity;

import net.micaxs.smokeleafindustry.fluid.ModFluids;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.item.custom.BaseWeedItem;
import net.micaxs.smokeleafindustry.recipe.HerbExtractorRecipe;
import net.micaxs.smokeleafindustry.screen.HerbExtractorMenu;
import net.micaxs.smokeleafindustry.utils.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

public class HerbExtractorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() instanceof BaseWeedItem;
                case 1 -> false; // Don't put stuff in output slot bro.
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static int INPUT_SLOT = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final ModEnergyStorage energy = new ModEnergyStorage(10000, 50, 50, 0);
    private final LazyOptional<ModEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.energy);
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final FluidTank FLUID_TANK = new FluidTank(8000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.SOURCE_HASH_OIL.get();
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

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, HerbExtractorBlockEntity pEntity) {
        boolean hasEnergy = energy.getEnergyStored() > 0;

        if(hasEnergy && hasRecipe()) {
            increaseCraftingProgress();
            energy.removeEnergy(10);

            if (pLevel.random.nextInt(2) == 0) {
                double x = pPos.getX() + 0.5;
                double y = pPos.getY() + 1.0;
                double z = pPos.getZ() + 0.5;

            }
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
        // Check if Fluid Tank is FULL
        if (FLUID_TANK.getFluidAmount() < FLUID_TANK.getCapacity()) {
            int filledAmount = FLUID_TANK.fill(new FluidStack(ModFluids.SOURCE_HASH_OIL.get(), 100), IFluidHandler.FluidAction.EXECUTE);
            if (filledAmount > 0) {
                itemHandler.extractItem(INPUT_SLOT, 1, false);
            }
        }
    }

    private boolean hasRecipe() {
        return !itemHandler.getStackInSlot(INPUT_SLOT).isEmpty();
    }


    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }


    public LazyOptional<ModEnergyStorage> getLazyEnergy () {
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

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }


}
