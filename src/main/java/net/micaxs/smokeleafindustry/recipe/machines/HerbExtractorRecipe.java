package net.micaxs.smokeleafindustry.recipe.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.utils.FluidJSONUtil;
import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerbExtractorRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final FluidStack output;
    private final ResourceLocation id;

    public HerbExtractorRecipe(NonNullList<Ingredient> inputItems, FluidStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
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
    public @NotNull ItemStack assemble(@NotNull SimpleContainer simpleContainer, @NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
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

    public FluidStack getResultFluid() {
        return this.output;
    }

    public static class Type implements RecipeType<HerbExtractorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "herb_extractor";
    }

    public static class Serializer implements RecipeSerializer<HerbExtractorRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "herb_extractor");

        @Override
        public HerbExtractorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            FluidStack output = FluidJSONUtil.readFluid(pSerializedRecipe.get("output").getAsJsonObject());

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new HerbExtractorRecipe(inputs, output, pRecipeId);
        }

        @Override
        public @Nullable HerbExtractorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            FluidStack output = pBuffer.readFluidStack();

            return new HerbExtractorRecipe(inputs, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HerbExtractorRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeFluidStack(pRecipe.getResultFluid());
        }
    }
}
