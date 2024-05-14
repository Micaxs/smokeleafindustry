package net.micaxs.smokeleafindustry.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class HempWeaverRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int count;

    public HempWeaverRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, int count) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.count = count;
    }

    @Override
    public boolean matches(SimpleContainer pSimpleContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        int itemCount = 0;
        for (int i = 0; i < pSimpleContainer.getContainerSize(); i++) {
            if (inputItems.get(0).test(pSimpleContainer.getItem(i))) {
                itemCount += pSimpleContainer.getItem(i).getCount();
            }
        }

        return itemCount >= getCount();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pSimpleContainer, RegistryAccess registryAccess) {
        // Iterate over the container's slots
        for (int i = 0; i < pSimpleContainer.getContainerSize(); i++) {
            // Check if the item in the slot matches the required ingredient
            if (inputItems.get(0).test(pSimpleContainer.getItem(i))) {
                // If it does, decrease the stack size by the required count
                pSimpleContainer.getItem(i).shrink(getCount());
                break;
            }
        }

        // Return a copy of the output item
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getCount() { // Add this method
        return count;
    }

    public static class Type implements RecipeType<HempWeaverRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID =  "hemp_weaver";
    }

    public static class Serializer implements RecipeSerializer<HempWeaverRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "hemp_weaver");

        @Override
        public HempWeaverRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            int count = 0; // Add this line
            for (int i = 0; i < inputs.size(); i++) {
                JsonObject ingredient = ingredients.get(i).getAsJsonObject();
                inputs.set(i, Ingredient.fromJson(ingredient));
                count = ingredient.get("count").getAsInt(); // Add this line
            }

            return new HempWeaverRecipe(inputs, output, pRecipeId, count); // Modify this line
        }

        @Override
        public @Nullable HempWeaverRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            int count = pBuffer.readInt();

            return new HempWeaverRecipe(inputs, output, pRecipeId, count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HempWeaverRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeInt(pRecipe.getCount());
        }
    }
}
