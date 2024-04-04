package net.micaxs.smokeleafindustry.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import java.util.Random;

public class ManualGrinderRecipe extends ShapelessRecipe {
    public ManualGrinderRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pGroup, pCategory, pResult, pIngredients);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess pRegistryAccess) {
        ItemStack result = super.assemble(inv, pRegistryAccess);
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item.getItem() == ModItems.GRINDER.get()) {
                if (item.getDamageValue() >= item.getMaxDamage() - 1) {
                    item.shrink(1);
                } else {
                    item.hurtAndBreak(1, null, playerEntity -> playerEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }
        return result;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level world) {
        boolean foundGrinder = false;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item.getItem() == ModItems.GRINDER.get()) {
                foundGrinder = true;
                if (item.getDamageValue() >= item.getMaxDamage() - 1) {
                    return false;
                }
            }
        }
        return foundGrinder && super.matches(inv, world);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ManualGrinderRecipe.Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<ManualGrinderRecipe> {
        public static final ManualGrinderRecipe.Serializer INSTANCE = new ManualGrinderRecipe.Serializer();

        @Override
        public ManualGrinderRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            // Parse the JSON object to get the recipe data
            String group = GsonHelper.getAsString(json, "group");
            ItemStack resultItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientsJson.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredientsJson.size(); i++) {
                ingredients.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
            }

            // Create a new ManualGrinderRecipe with the data and return it
            return new ManualGrinderRecipe(recipeId, group, CraftingBookCategory.MISC, resultItem, ingredients);
        }

        @Override
        public ManualGrinderRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            ItemStack resultItem = buffer.readItem();

            int ingredientsSize = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientsSize, Ingredient.EMPTY);
            for (int i = 0; i < ingredientsSize; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buffer));
            }

            return new ManualGrinderRecipe(recipeId, group, CraftingBookCategory.MISC, resultItem, ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ManualGrinderRecipe recipe) {
            buffer.writeResourceLocation(recipe.getId());
            buffer.writeUtf(recipe.getGroup());
            buffer.writeItem(recipe.getResultItem(null));

            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }
        }
    }
}
