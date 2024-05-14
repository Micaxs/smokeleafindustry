package net.micaxs.smokeleafindustry.block.entity;

import net.micaxs.smokeleafindustry.utils.ModEnergyStorage;

import net.micaxs.smokeleafindustry.fluid.ModFluids;
import net.micaxs.smokeleafindustry.recipe.machines.HerbMutationRecipe;
import net.micaxs.smokeleafindustry.screen.HerbMutationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.item.crafting.Ingredient;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HerbMutationBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
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
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 1 -> true;
                case 3 -> false; // Don't put stuff in output slot bro.
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private static int FLUID_INPUT_SLOT = 0;
    private static int INPUT_SLOT = 1;
    private static int SECONDARY_INPUT_SLOT = 2;
    private static int OUTPUT_SLOT = 3;


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
            return stack.getFluid() == ModFluids.SOURCE_HASH_OIL.get();
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;



    public HerbMutationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HERB_MUTATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HerbMutationBlockEntity.this.progress;
                    case 1 -> HerbMutationBlockEntity.this.maxProgress;
                    case 2 -> HerbMutationBlockEntity.this.energy.getEnergyStored();
                    case 3 -> HerbMutationBlockEntity.this.energy.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HerbMutationBlockEntity.this.progress = pValue;
                    case 1 -> HerbMutationBlockEntity.this.maxProgress = pValue;
                    case 2 -> HerbMutationBlockEntity.this.energy.setEnergy(pValue);
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
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        } else if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) {
            return itemHandler.getStackInSlot(FLUID_INPUT_SLOT).getCapability(cap, side);
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
        return Component.translatable("block.smokeleafindustry.herb_mutation");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HerbMutationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("herb_mutation.progress", progress);
        pTag.put("energy", this.energy.serializeNBT());
        pTag = FLUID_TANK.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energy.deserializeNBT(pTag.get("energy"));
        progress = pTag.getInt("herb_mutation.progress");
        FLUID_TANK.readFromNBT(pTag);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, HerbMutationBlockEntity pEntity) {
        boolean hasEnergy = energy.getEnergyStored() > 0;

        if (hasFluidItemInSourceSlot(pEntity)) {
            transferItemFluidToFluidTank(pEntity);
        }

        
        if(hasEnergy && hasRecipe()) {
            increaseCraftingProgress();
            energy.removeEnergy(20);

            if (pLevel.random.nextInt(2) == 0) {
                double x = pPos.getX() + 0.5;
                double y = pPos.getY() + 1.0;
                double z = pPos.getZ() + 0.5;

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


    private void transferItemFluidToFluidTank(HerbMutationBlockEntity pEntity) {
        pEntity.itemHandler.getStackInSlot(FLUID_INPUT_SLOT).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {

            if (!FLUID_TANK.getFluid().isFluidEqual(handler.getFluidInTank(0)) && FLUID_TANK.getFluidAmount() != 0) {
                return;
            }

            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);
            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if (pEntity.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, stack, handler.getContainer());
            }
        });
    }

    private void fillTankWithFluid(HerbMutationBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private boolean hasFluidItemInSourceSlot(HerbMutationBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(FLUID_INPUT_SLOT).getCount() > 0;
    }


    private void resetProgress() {
        progress = 0;
    }
    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress() {
        progress++;
    }

    private void craftItem() {
        Optional<HerbMutationRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        NonNullList<Ingredient> inputItems = recipe.get().getIngredients();

        removeCorrectItemsFromInputSlots(inputItems);

        FLUID_TANK.drain(recipe.get().getFluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
        
    }

    private void removeCorrectItemsFromInputSlots(NonNullList<Ingredient> inputItems) {
        for (int i = 0; i < inputItems.size(); i++) {
            if (i == 0) {
                int count = inputItems.get(0).getItems()[0].getCount();
                this.itemHandler.extractItem(INPUT_SLOT, count, false);
            }
            if (i == 1) {
                int count = inputItems.get(1).getItems()[0].getCount();
                this.itemHandler.extractItem(SECONDARY_INPUT_SLOT, count, false);
            }
        }
    }

    private boolean hasRecipe() {
        Optional<HerbMutationRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(null);

        return  hasCorrectAmountsInInputSlots() && canInsertAmountIntoOutputSlot(result.getCount()) &&
                canInsertItemIntoOutputSlot(result.getItem()) &&
                hasCorrectFluidInTank(recipe) &&
                hasCorrectFluidAmountInTank(recipe);
    }

    private boolean hasCorrectAmountsInInputSlots() {
        Optional<HerbMutationRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        NonNullList<Ingredient> inputItems = recipe.get().getIngredients();

        for (int i = 0; i < inputItems.size(); i++) {
            Ingredient ingredient = inputItems.get(i);
            ItemStack[] matchingStacks = ingredient.getItems();
            int count = (matchingStacks.length > 0) ? matchingStacks[0].getCount() : 0;

            int slotIndex = i + 1;
            ItemStack itemStackInSlot = itemHandler.getStackInSlot(slotIndex);
            if (itemStackInSlot.getCount() < count) {
                return false;
            }
        }

        return true;
    }




    private boolean hasCorrectFluidInTank(Optional<HerbMutationRecipe> recipe) {
        return recipe.filter(herbMutationRecipe -> {
            FluidStack tankFluid = FLUID_TANK.getFluid();
            FluidStack recipeFluid = herbMutationRecipe.getFluid();
            return tankFluid.isFluidEqual(recipeFluid);
        }).isPresent();
    }

    private boolean hasCorrectFluidAmountInTank(Optional<HerbMutationRecipe> recipe) {
        return FLUID_TANK.getFluidAmount() >= recipe.get().getFluid().getAmount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }



    private Optional<HerbMutationRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(HerbMutationRecipe.Type.INSTANCE, inventory, level);
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

    private void spawnParticles(Level level, BlockPos pos) {
        // Adjust the particle position based on the direction you want the particles to go
        double x = pos.getX();
        double y = pos.getY() + 0.9;
        double z = pos.getZ();

        level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
    }

}
