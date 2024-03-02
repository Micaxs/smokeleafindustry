package net.micaxs.smokeleafindustry.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class CustomFuelSlot extends SlotItemHandler {
    private final Predicate<ItemStack> fuelPredicate;
    public CustomFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, xPosition, yPosition, stack -> ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0);
    }

    public CustomFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> fuelPredicate) {
        super(itemHandler, index, xPosition, yPosition);
        this.fuelPredicate = fuelPredicate;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return fuelPredicate.test(stack);
    }
}
