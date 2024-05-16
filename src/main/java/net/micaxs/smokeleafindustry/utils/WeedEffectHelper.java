package net.micaxs.smokeleafindustry.utils;

import net.micaxs.smokeleafindustry.item.custom.BaseWeedItem;
import net.micaxs.smokeleafindustry.item.custom.WeedDerivedItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class WeedEffectHelper {
    public static void setWeedNBTData(CraftingContainer pContainer, ItemStack weedDerivedItem) {
        int finalDuration = 0;
        for (ItemStack ingredient : pContainer.getItems()) {
            if (!(ingredient.getItem() instanceof BaseWeedItem weedItem) || !(weedDerivedItem.getItem() instanceof WeedDerivedItem)) {
                continue;
            }

            CompoundTag weedBasedItemTag = weedDerivedItem.getShareTag();
            if (weedBasedItemTag == null) {
                weedBasedItemTag = new CompoundTag();
            }

            // Set duration tag
            float effectMultiplier = ((WeedDerivedItem) weedDerivedItem.getItem()).getEffectFactor();
            finalDuration += weedItem.getDuration() * effectMultiplier;
            weedBasedItemTag.putInt("duration", finalDuration);

            // Set the item used to produce the product
            weedBasedItemTag.putString("active_ingredient", ingredient.getItem().getDescriptionId());

            weedDerivedItem.setTag(weedBasedItemTag);

            nameWeedBasedItem(ingredient, weedDerivedItem);
        }
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

    public static Component getEffectTooltip(MobEffect effect, int effectDuration, boolean showDuration) {
        if (showDuration) {
            int duration = effectDuration / 20; // Convert duration to seconds
            return Component.translatable(effect.getDescriptionId())
                    .append(" ")
                    .append(Component.literal("(" + duration + "s)"))
                    .withStyle(ChatFormatting.GREEN);
        }

        return Component.translatable(effect.getDescriptionId())
                .append(" ")
                .append(Component.literal("(??)"))
                .withStyle(ChatFormatting.GREEN);
    }
}
