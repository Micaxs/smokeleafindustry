package net.micaxs.smokeleafindustry.block.entity;

import net.micaxs.smokeleafindustry.item.custom.BaseWeedItem;
import net.micaxs.smokeleafindustry.recipe.HerbEvaporatorRecipe;
import net.micaxs.smokeleafindustry.screen.HerbEvaporatorMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HerbEvaporatorBlockEntity extends BlockEntity implements MenuProvider {

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
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static int INPUT_SLOT = 0;
    private static int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final ModEnergyStorage energy = new ModEnergyStorage(10000, 50, 50, 0);
    private final LazyOptional<ModEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.energy);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public HerbEvaporatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HERB_EVAPORATOR_BE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HerbEvaporatorBlockEntity.this.progress;
                    case 1 -> HerbEvaporatorBlockEntity.this.maxProgress;
                    case 2 -> HerbEvaporatorBlockEntity.this.energy.getEnergyStored();
                    case 3 -> HerbEvaporatorBlockEntity.this.energy.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HerbEvaporatorBlockEntity.this.progress = pValue;
                    case 1 -> HerbEvaporatorBlockEntity.this.maxProgress = pValue;
                    case 2 -> HerbEvaporatorBlockEntity.this.energy.setEnergy(pValue);
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
            if (side == null) {
                return lazyItemHandler.cast();
            } else {
                return LazyOptional.of(() -> new IItemHandler() {
                    @Override
                    public int getSlots() {
                        return itemHandler.getSlots();
                    }
                    @Override
                    public ItemStack getStackInSlot(int slot) {
                        return itemHandler.getStackInSlot(slot);
                    }
                    @Override
                    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                        return itemHandler.insertItem(slot, stack, simulate);
                    }
                    @Override
                    public ItemStack extractItem(int slot, int amount, boolean simulate) {
                        if (slot == INPUT_SLOT) {
                            return ItemStack.EMPTY;
                        }
                        return itemHandler.extractItem(slot, amount, simulate);
                    }
                    @Override
                    public int getSlotLimit(int slot) {
                        return itemHandler.getSlotLimit(slot);
                    }
                    @Override
                    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                        return itemHandler.isItemValid(slot, stack);
                    }
                }).cast();
            }
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
        return Component.translatable("block.smokeleafindustry.herb_evaporator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HerbEvaporatorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("herb_evaporator.progress", progress);
        pTag.put("energy", this.energy.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energy.deserializeNBT(pTag.get("energy"));
        progress = pTag.getInt("herb_evaporator.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean hasEnergy = energy.getEnergyStored() > 0;

        if(hasEnergy && hasRecipe()) {
            increaseCraftingProgress();

            energy.removeEnergy(10);

            if (pLevel.random.nextInt(2) == 0) {
                double x = pPos.getX() + 0.5;
                double y = pPos.getY() + 1.0;
                double z = pPos.getZ() + 0.5;
                pLevel.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
            }

            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<HerbEvaporatorRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private Optional<HerbEvaporatorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(HerbEvaporatorRecipe.Type.INSTANCE, inventory, level);
    }

    private void resetProgress() {
        progress = 0;
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
