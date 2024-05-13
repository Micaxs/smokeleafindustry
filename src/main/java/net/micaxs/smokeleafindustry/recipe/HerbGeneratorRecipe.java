package net.micaxs.smokeleafindustry.recipe;

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

public class HerbGeneratorRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int outputEnergy;
    private final ResourceLocation id;

    public HerbGeneratorRecipe(NonNullList<Ingredient> inputItems, int output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.outputEnergy = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pSimpleContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputItems.get(0).test(pSimpleContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getResultEnergy() {
        return this.outputEnergy;
    }

    public static class Type implements RecipeType<HerbGeneratorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID =  "herb_recipe";
    }

    public static class Serializer implements RecipeSerializer<HerbGeneratorRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "herb_recipe");

        @Override
        public HerbGeneratorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            int output = GsonHelper.getAsJsonObject(pSerializedRecipe, "output").get("amount").getAsInt();

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new HerbGeneratorRecipe(inputs, output, pRecipeId);
        }

        @Override
        public @Nullable HerbGeneratorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            int outputEnergy = pBuffer.readInt();

            return new HerbGeneratorRecipe(inputs, outputEnergy, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HerbGeneratorRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeInt(pRecipe.getResultEnergy());
        }
    }
}
