package net.micaxs.smokeleaf.item.custom;

import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;

public class BaseBudItem extends Item {

    public int dry;
    public int dryingTime;

    public BaseBudItem(Properties properties, int iDry, int iDryingTime) {
        super(properties);
        this.dry = iDry;
        this.dryingTime = iDryingTime;
    }

    public boolean isDry() {
        return dry != 0;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = super.getName(stack);
        if (stack.get(ModDataComponentTypes.DRY) != null) {
            if (Boolean.TRUE.equals(stack.get(ModDataComponentTypes.DRY))) {
                return Component.translatable("tooltip.smokeleafindustries.dried").append(" ").append(baseName);
            } else {
                return Component.translatable("tooltip.smokeleafindustries.fresh").append(" ").append(baseName);
            }
        } else {
            return Component.translatable("tooltip.smokeleafindustries.fresh").append(" ").append(baseName);
        }
    }


    // Helper to set dry.
    public static void changeDryStatus(ItemStack stack, boolean value) {
        stack.set(ModDataComponentTypes.DRY, value);
    }

}
