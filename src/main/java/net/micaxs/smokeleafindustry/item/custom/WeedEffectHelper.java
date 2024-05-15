package net.micaxs.smokeleafindustry.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class WeedEffectHelper {
    public static void addWeedEffectToItem(ItemStack weedStack, ItemStack weedBasedItem) {
        if (!(weedStack.getItem() instanceof BaseWeedItem) || !(weedBasedItem.getItem() instanceof WeedDerivedItem)) {
            return;
        }

        CompoundTag weedDataTag = weedStack.getShareTag();
        if (weedDataTag == null) {
            weedDataTag = new CompoundTag();
        }

        int previousDuration = 0;
        CompoundTag weedBasedItemTag = weedBasedItem.getShareTag();
        if (weedBasedItemTag == null) {
            weedBasedItemTag = new CompoundTag();
            weedBasedItem.setTag(weedBasedItemTag);
        } else if (weedBasedItemTag.contains("duration")) {
            previousDuration = weedBasedItemTag.getInt("duration");
        }

        weedBasedItem.setTag(weedDataTag.copy());
        if (weedDataTag.contains("duration")) {
            weedBasedItem.getShareTag().putInt(
                    "duration",
                    (int) ((weedDataTag.getInt("duration") * ((WeedDerivedItem) weedBasedItem.getItem()).getEffectFactor()) + previousDuration)
            );
        }
    }

    public static void nameWeedBasedItem(ItemStack weedStack, ItemStack weedBasedItem) {
        if (!(weedStack.getItem() instanceof BaseWeedItem)) {
            return;
        }

        Component weedNameComponent = weedStack.getItem().getName(weedStack);
        String weedName = weedNameComponent.getString().replace(" Weed", "").replace(" weed", "");
        weedBasedItem.setHoverName(Component.translatable(weedBasedItem.getDescriptionId(), weedName));
    }
}
