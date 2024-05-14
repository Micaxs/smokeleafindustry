package net.micaxs.smokeleafindustry.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.micaxs.smokeleafindustry.item.custom.WeedEffectHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BluntCraftingRecipe extends ShapedRecipe {
    private final ItemStack result;

    public BluntCraftingRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
        super(pId, pGroup, pCategory, pWidth, pHeight, pRecipeItems, pResult);
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
        return BluntCraftingRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer pContainer, @NotNull RegistryAccess pRegistry) {
        ItemStack output = super.assemble(pContainer, pRegistry);
        if (output.isEmpty()) {
            return output;
        }

        for (ItemStack ingredient : pContainer.getItems()) {
            WeedEffectHelper.addWeedEffectToItem(ingredient, output);
            WeedEffectHelper.nameWeedBasedItem(ingredient, output);
        }

        return output;
    }

    public static class Serializer implements RecipeSerializer<BluntCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull BluntCraftingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray patternArray = GsonHelper.getAsJsonArray(json, "pattern");
            String[] pattern = new String[patternArray.size()];
            for (int i = 0; i < patternArray.size(); i++) {
                pattern[i] = patternArray.get(i).getAsString();
            }

            JsonObject keyObject = GsonHelper.getAsJsonObject(json, "key");
            Map<Character, Ingredient> key = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : keyObject.entrySet()) {
                if (entry.getKey().length() != 1) {
                    throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                }
                if (" ".equals(entry.getKey())) {
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                }

                key.put(entry.getKey().charAt(0), Ingredient.fromJson(entry.getValue()));
            }

            int width = pattern[0].length();
            int height = pattern.length;
            NonNullList<Ingredient> inputs = NonNullList.withSize(width * height, Ingredient.EMPTY);
            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern[i].length(); j++) {
                    inputs.set(i * width + j, key.get(pattern[i].charAt(j)));
                }
            }

            return new BluntCraftingRecipe(recipeId, "", CraftingBookCategory.MISC, width, height, inputs, output);
        }

        @Override
        public BluntCraftingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int numIngredients = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(numIngredients, Ingredient.EMPTY);

            for (int i = 0; i < numIngredients; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();

            return new BluntCraftingRecipe(recipeId, "", CraftingBookCategory.MISC, 3, 3, inputs, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BluntCraftingRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
        }
    }
}
