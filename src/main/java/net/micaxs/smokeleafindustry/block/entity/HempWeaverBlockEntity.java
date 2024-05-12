package net.micaxs.smokeleafindustry.block.entity;


import net.micaxs.smokeleafindustry.recipe.HempWeaverRecipe;
import net.micaxs.smokeleafindustry.screen.HempWeaverMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HempWeaverBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
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
                case 0 -> {
                    List<HempWeaverRecipe> recipes = level.getRecipeManager().getAllRecipesFor(HempWeaverRecipe.Type.INSTANCE);
                    for (HempWeaverRecipe recipe : recipes) {
                        if (recipe.matches(new SimpleContainer(stack), level)) {
                            yield true;
                        }
                    }
                    yield false;
                }
                case 1 -> false; // Don't put stuff in output slot pls.
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
    private int maxProgress = 78;

    public HempWeaverBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HEMP_WEAVER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HempWeaverBlockEntity.this.progress;
                    case 1 -> HempWeaverBlockEntity.this.maxProgress;
                    case 2 -> HempWeaverBlockEntity.this.energy.getEnergyStored();
                    case 3 -> HempWeaverBlockEntity.this.energy.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HempWeaverBlockEntity.this.progress = pValue;
                    case 1 -> HempWeaverBlockEntity.this.maxProgress = pValue;
                    case 2 -> HempWeaverBlockEntity.this.energy.setEnergy(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStack() {
        if (itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
            return itemHandler.getStackInSlot(INPUT_SLOT);
        } else {
            return itemHandler.getStackInSlot(OUTPUT_SLOT);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            } else {
                return LazyOptional.of(() -> new IItemHandlerModifiable() {
                    @Override
                    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
                        itemHandler.setStackInSlot(slot, stack);
                    }

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
                        if (slot != OUTPUT_SLOT) {
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
        return Component.translatable("block.smokeleafindustry.hemp_weaver");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HempWeaverMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("hemp_spinner.progress", progress);
        pTag.put("energy", this.energy.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energy.deserializeNBT(pTag.get("energy"));

        progress = pTag.getInt("hemp_weaver.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean hasEnergy = energy.getEnergyStored() > 0;

        if(hasEnergy && hasRecipe()) {
            increaseCraftingProgress();

            energy.removeEnergy(20);

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

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<HempWeaverRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);

            this.itemHandler.extractItem(INPUT_SLOT, recipe.get().getCount(), false);
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
        }
    }

    private boolean hasRecipe() {
        Optional<HempWeaverRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<HempWeaverRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(HempWeaverRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
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

    private void spawnParticles(Level level, BlockPos pos) {
        // Adjust the particle position based on the direction you want the particles to go
        double x = pos.getX();
        double y = pos.getY() + 0.9;
        double z = pos.getZ();

        level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
    }

}
