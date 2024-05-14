package net.micaxs.smokeleafindustry.block.entity;


import net.micaxs.smokeleafindustry.recipe.machines.HerbGeneratorRecipe;
import net.micaxs.smokeleafindustry.utils.ModEnergyStorage;
import net.micaxs.smokeleafindustry.screen.HerbGeneratorMenu;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HerbGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private static final int ENERGY_CONSTANT = 40;
    private static final int FUEL_SLOT = 0;
    private int burnTime = 0, maxBurnTime = 0;
    protected final ContainerData data;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 1);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == 0) {
                return canBurn(stack);
            }
            return false;
        }
    };
    private final ModEnergyStorage energy = new ModEnergyStorage(10000, 0, 100, 0);
    private final LazyOptional<ModEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.energy);

    public HerbGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HERB_GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HerbGeneratorBlockEntity.this.burnTime;
                    case 1 -> HerbGeneratorBlockEntity.this.maxBurnTime;
                    case 2 -> HerbGeneratorBlockEntity.this.energy.getEnergyStored();
                    case 3 -> HerbGeneratorBlockEntity.this.energy.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HerbGeneratorBlockEntity.this.burnTime = pValue;
                    case 1 -> HerbGeneratorBlockEntity.this.maxBurnTime = pValue;
                    case 2 -> HerbGeneratorBlockEntity.this.energy.setEnergy(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(FUEL_SLOT);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        } else if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergy.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergy.invalidate();
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
        return Component.translatable("block.smokeleafindustry.herb_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HerbGeneratorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public ModEnergyStorage getEnergy() {
        return this.energy;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("herb_generator.energy", this.energy.serializeNBT());
        pTag.putInt("herb_generator.burnTime", burnTime);
        pTag.putInt("herb_generator.maxBurnTime", maxBurnTime);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energy.deserializeNBT(pTag.get("herb_generator.energy"));
        burnTime = pTag.getInt("herb_generator.burnTime");
        maxBurnTime = pTag.getInt("herb_generator.maxBurnTime");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        // Sets block state to POWERED if producing energy
        if (getBlockState().getValue(BlockStateProperties.POWERED) != burnTime > 0) {
            if (pLevel != null) {
                pLevel.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, burnTime > 0));
            }
        }

        // Generate power if conditions are correct
        if (this.energy.getEnergyStored() < this.energy.getMaxEnergyStored()) {
            if (this.burnTime <= 0) {
                // Burn new item if possible
                if (canBurn(this.itemHandler.getStackInSlot(FUEL_SLOT))) {
                    this.burnTime = this.maxBurnTime = getBurnTime();
                    this.itemHandler.getStackInSlot(FUEL_SLOT).shrink(1);
                    setChanged();
                }
            } else {
                // Generate energy
                this.burnTime--;
                this.energy.addEnergy(ENERGY_CONSTANT);
                setChanged();
            }
        }

        // Send energy to adjacent blocks if possible
        distributeEnergy();
    }

    public int getBurnTime() {
        Optional<HerbGeneratorRecipe> recipe = getCurrentRecipe();
        return recipe.get().getResultEnergy() / ENERGY_CONSTANT;
    }

    public boolean canBurn(ItemStack stack) {
        List<HerbGeneratorRecipe> recipes = level.getRecipeManager().getAllRecipesFor(HerbGeneratorRecipe.Type.INSTANCE);
        for (HerbGeneratorRecipe recipe : recipes) {
            if (recipe.matches(new SimpleContainer(stack), level)) {
                return true;
            }
        }
        return false;
    }

    private Optional<HerbGeneratorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(HerbGeneratorRecipe.Type.INSTANCE, inventory, level);
    }

    private void distributeEnergy() {
        // Check all sides of the block and send energy if that block supports the energy capability
        for (Direction direction : Direction.values()) {
            if (energy.getEnergyStored() <= 0) {
                return;
            }
            BlockEntity be = level.getBlockEntity(getBlockPos().relative(direction));
            if (be != null) {
                be.getCapability(ForgeCapabilities.ENERGY).map(e -> {
                    if (e.canReceive()) {
                        int received = e.receiveEnergy(Math.min(energy.getEnergyStored(), 50), false);
                        energy.removeEnergy(received);
                        setChanged();
                        return received;
                    }
                    return 0;
                });
            }
        }
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
