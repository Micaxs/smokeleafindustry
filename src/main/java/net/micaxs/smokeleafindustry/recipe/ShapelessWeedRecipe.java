package net.micaxs.smokeleafindustry.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class ShapelessWeedRecipe extends ShapelessRecipe {
    private final ItemStack result;

    public ShapelessWeedRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pGroup, pCategory, pResult, pIngredients);
        this.result = pResult;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ShapelessWeedRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer pContainer, @NotNull RegistryAccess pRegistry) {
        ItemStack output = super.assemble(pContainer, pRegistry);
        if (output.isEmpty()) {
            return output;
        }
        WeedEffectHelper.setWeedNBTData(pContainer, output);
        return output;
    }

    public static class Serializer implements RecipeSerializer<ShapelessWeedRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull ShapelessWeedRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientsJson.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredientsJson.size(); i++) {
                ingredients.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
            }

            return new ShapelessWeedRecipe(recipeId, "", CraftingBookCategory.MISC, output, ingredients);
        }

        @Override
        public ShapelessWeedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int numIngredients = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(numIngredients, Ingredient.EMPTY);

            for (int i = 0; i < numIngredients; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();

            return new ShapelessWeedRecipe(recipeId, "", CraftingBookCategory.MISC, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ShapelessWeedRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
        }
    }
}
