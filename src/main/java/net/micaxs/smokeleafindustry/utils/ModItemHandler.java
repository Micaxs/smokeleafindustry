package net.micaxs.smokeleafindustry.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ModItemHandler extends ItemStackHandler {
    private static int INPUT_SLOT = 0;
    private static int OUTPUT_SLOT = 1;

    public ModItemHandler(int size) {
        super(size);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        // Allow insertion into any slot
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        // If extraction is being done from the OUTPUT_SLOT, then allow the extraction
        if (slot == OUTPUT_SLOT) {
            return super.extractItem(slot, amount, simulate);
        }

        // If extraction is being done from the INPUT_SLOT, then check if it's being done manually or programmatically
        if (slot == INPUT_SLOT) {
            // If it's being done manually, then allow the extraction
            if (!simulate) {
                return super.extractItem(slot, amount, simulate);
            }

            // If it's being done by a hopper or any other automated system, then prevent the extraction
            return ItemStack.EMPTY;
        }

        return super.extractItem(slot, amount, simulate);
    }
}