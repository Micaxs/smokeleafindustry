package net.micaxs.smokeleafindustry.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.item.custom.BaseWeedItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HashOilHelper {
    public static boolean setWeedNBTDataFluid(ItemStack itemHandler, FluidStack weedDerivedFluid) {
        BaseWeedItem weedItem = (BaseWeedItem) itemHandler.getItem();

        CompoundTag fluidTag = weedDerivedFluid.getTag();
        if (fluidTag == null) {
            fluidTag = new CompoundTag();
        }

        // Check if oil already has active ingredients
        String previousData = fluidTag.getString("active_ingredients");
        JsonObject activeIngredientsTag = new JsonObject();
        if (!previousData.equals("")) {
            activeIngredientsTag = JsonParser.parseString(fluidTag.getString("active_ingredients")).getAsJsonObject();
        }

        // Check if 2 active ingredients are already in the set and exit if it is.
        if (activeIngredientsTag.keySet().size() >= 2) {
            return true;
        }

        // Set duration tag
        // TODO: duration is stored in the key, doesn't need to be here
        if (weedItem.getEffect() == MobEffects.LEVITATION) {
            activeIngredientsTag.addProperty(weedItem.getDescriptionId(), 200);
        } else {
            activeIngredientsTag.addProperty(weedItem.getDescriptionId(), 400);
        }

        fluidTag.putString("active_ingredients", activeIngredientsTag.toString());
        weedDerivedFluid.setTag(fluidTag);
        return false;
    }

    public static void transferWeedFluidNBTToBucket(ItemStack hashOilBucket, FluidStack hashOilFluid) {
        // Transfer weed effects
        CompoundTag hashOilBucketTag = hashOilBucket.getShareTag();
        if (hashOilBucketTag == null) {
            hashOilBucketTag = new CompoundTag();
        }
        hashOilBucketTag.merge(hashOilFluid.getOrCreateTag());
        hashOilBucket.setTag(hashOilBucketTag);
    }

    public static Component getHashOilName(List<BaseWeedItem> activeWeedIngredients, String translatableBaseKey, String translatableBlendKey) {
        // one 1 weed was used
        if (activeWeedIngredients.size() == 1) {
            BaseWeedItem activeIngredient = activeWeedIngredients.get(0);
            Component weedNameComponent = activeIngredient.getName(activeIngredient.getDefaultInstance());
            String weedName = weedNameComponent.getString().replace(" Weed", "").replace(" weed", "");
            return Component.literal(weedName).append(" ").append(Component.translatable(translatableBaseKey));
        }

        // 2 weeds
        if (activeWeedIngredients.size() == 2) {
            return Component.translatable(
                    translatableBlendKey,
                    activeWeedIngredients.get(0).getWeedNameParts()[0],
                    activeWeedIngredients.get(1).getWeedNameParts()[1]
            );
        }

        return Component.literal("nono");
    }

    public static List<BaseWeedItem> getActiveWeedIngredient(FluidStack fluidStack) {
        CompoundTag tag = fluidStack.getTag();
        if (tag == null || !tag.contains("active_ingredients")) {
            return List.of();
        }

        // extract the active ingredients
        JsonObject activeIngredientsTag = JsonParser.parseString(tag.getString("active_ingredients")).getAsJsonObject();
        Set<String> activeIngredients = activeIngredientsTag.keySet();
        if (activeIngredients.size() >= 3) {
            // Blend is "dead" if more than 3 types are mixed
            return List.of();
        }

        List<BaseWeedItem> result = new ArrayList<>();
        for (String activeIngredientName : activeIngredients) {
            String[] parts = activeIngredientName.split("\\.");
            activeIngredientName = parts[parts.length - 1];
            RegistryObject<Item> activeIngredient = RegistryObject.create(new ResourceLocation(SmokeleafIndustryMod.MOD_ID, activeIngredientName), ForgeRegistries.ITEMS);
            if (activeIngredient.get() instanceof BaseWeedItem activeIngredientItem) {
                activeIngredientItem.setVariableDuration(false);
                result.add(activeIngredientItem);
            }
        }

        return result;
    }
}
