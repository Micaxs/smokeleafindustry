package net.micaxs.smokeleaf.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.item.custom.BaseWeedItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class HashOilHelper {

    public static final int MAX_ACTIVE_INGREDIENTS = 2;

    public static boolean setWeedDataFluid(ItemStack itemHandler, FluidStack weedDerivedFluid) { // setWeedNBTDataFluid
        BaseWeedItem weedItem = (BaseWeedItem) itemHandler.getItem();

        JsonArray activeIngredients = weedDerivedFluid.get(ModDataComponentTypes.ACTIVE_INGREDIENTS);
        if (activeIngredients == null) {
            activeIngredients = new JsonArray();
        }

        // Return if it already has 2 active ingredients.
        if (activeIngredients.size() >= MAX_ACTIVE_INGREDIENTS) {
            return true;
        }

        // Add active ingredient to list
        if (!activeIngredients.contains(JsonParser.parseString(weedItem.getDescriptionId()))) {
            activeIngredients.add(weedItem.getDescriptionId());
        }

        weedDerivedFluid.set(ModDataComponentTypes.ACTIVE_INGREDIENTS, activeIngredients);
        return false;
    }


    public static void transferWeedFluidDataToBucket(ItemStack hashOilBucket, FluidStack hashOilFluid) { // transferWeedFluidNBTToBucket
        // Transfer weed effects
        var active = hashOilFluid.get(ModDataComponentTypes.ACTIVE_INGREDIENTS);
        if (active != null && !active.isEmpty()) {
            // Avoid shared mutable JsonArray
            hashOilBucket.set(ModDataComponentTypes.ACTIVE_INGREDIENTS, active.deepCopy());
        } else {
            hashOilBucket.remove(ModDataComponentTypes.ACTIVE_INGREDIENTS);
        }
    }


    public static Component getHashOilName(List<BaseWeedItem> activeWeedIngredients, String translatableBaseKey, String translatableBlendKey) {
        // One Weed
        if (activeWeedIngredients.size() == 1) {
            BaseWeedItem activeIngredient = activeWeedIngredients.getFirst();
            Component weedNameComponent = activeIngredient.getName(activeIngredient.getDefaultInstance());
            String weedName = weedNameComponent.getString().replace(" Weed", "").replace(" weed", "");
            return Component.literal(weedName).append(" ").append(Component.translatable(translatableBaseKey));
        }

        // Two Weeds
        if (activeWeedIngredients.size() == 2) {
            return Component.translatable(
                    translatableBlendKey,
                    activeWeedIngredients.get(0).getWeedNameParts()[0],
                    activeWeedIngredients.get(1).getWeedNameParts()[1]
            );
        }

        return Component.literal("nono");
    }



//    public static List<BaseWeedItem> getActiveWeedIngredient(FluidStack fluidStack) {
//        return fluidStack.get(ModDataComponentTypes.ACTIVE_INGREDIENTS);
//    }

    public static List<BaseWeedItem> getActiveWeedIngredient(FluidStack fluidStack) {
        JsonArray activeIngredients = fluidStack.get(ModDataComponentTypes.ACTIVE_INGREDIENTS);
        if (activeIngredients == null || activeIngredients.size() == 0) {
            return List.of();
        }

        if (activeIngredients.size() >= 3) {
            // Blend is "dead" if more than 3 types are mixed
            return List.of();
        }

        List<BaseWeedItem> result = new ArrayList<>();
        for (JsonElement activeIngredientElement : activeIngredients.asList()) {
            String[] parts = activeIngredientElement.getAsString().split("\\.");
            String activeIngredientName = parts[parts.length - 1];
            DeferredHolder<Item, Item> activeIngredient = DeferredHolder.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, activeIngredientName));
            if (activeIngredient.get() instanceof BaseWeedItem activeIngredientItem) {
                activeIngredientItem.setVariableDuration(false);
                activeIngredientItem.setDurationMultiplier(2);
                result.add(activeIngredientItem);
            }
        }

        return result;
    }


}
