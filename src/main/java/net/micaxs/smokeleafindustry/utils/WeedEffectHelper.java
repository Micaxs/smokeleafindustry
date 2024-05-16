package net.micaxs.smokeleafindustry.utils;

import net.micaxs.smokeleafindustry.item.custom.BaseWeedItem;
import net.micaxs.smokeleafindustry.item.custom.WeedDerivedItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class WeedEffectHelper {
    public static void addWeedEffectToItem(ItemStack weedStack, ItemStack weedBasedItem) {
        // Make sure item stacks are valid for these operations
        if (!(weedStack.getItem() instanceof BaseWeedItem) || !(weedBasedItem.getItem() instanceof WeedDerivedItem)) {
            return;
        }

        // Make sure item stacks have compound tags
        CompoundTag weedDataTag = weedStack.getShareTag();
        if (weedDataTag == null) {
            weedDataTag = new CompoundTag();
        }
        CompoundTag weedBasedItemTag = weedBasedItem.getShareTag();
        if (weedBasedItemTag == null) {
            weedBasedItemTag = new CompoundTag();
            weedBasedItem.setTag(weedBasedItemTag);
        }

        // Get previous duration of effect for effect stacking
        int previousDuration = getPreviousDuration(weedBasedItem);

        // Copy over nbt tags from base weed to weed derived item (kinda useless atm, really just copies CBD and THC values)
        weedBasedItem.setTag(weedDataTag.copy());

        // Set duration tag + previous duration
        if (weedDataTag.contains("duration")) {
            float effectMultiplier = ((WeedDerivedItem) weedBasedItem.getItem()).getEffectFactor();
            int finalDuration = (int) (weedDataTag.getInt("duration") * effectMultiplier) + previousDuration;
            weedBasedItem.getShareTag().putInt("duration", finalDuration);
        }
    }

    private static int getPreviousDuration(ItemStack weedBasedItem) {
        int previousDuration = 0;
        CompoundTag weedBasedItemTag = weedBasedItem.getShareTag();
        if (weedBasedItemTag == null) {
            weedBasedItemTag = new CompoundTag();
            weedBasedItem.setTag(weedBasedItemTag);
        } else if (weedBasedItemTag.contains("duration")) {
            previousDuration = weedBasedItemTag.getInt("duration");
        }
        return previousDuration;
    }

    public static void nameWeedBasedItem(ItemStack weedStack, ItemStack weedBasedItem) {
        if (!(weedStack.getItem() instanceof BaseWeedItem)) {
            return;
        }

        Component weedNameComponent = weedStack.getItem().getName(weedStack);
        Component weedBasedItemName = weedBasedItem.getItem().getName(weedBasedItem);
        String weedName = weedNameComponent.getString().replace(" Weed", "").replace(" weed", "");
        weedBasedItem.setHoverName(Component.literal(weedName.concat(" ").concat(weedBasedItemName.getString())));
    }
}
