package net.micaxs.smokeleafindustry.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.utils.FluidJSONUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class HerbMutationRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final FluidStack fluidStack;

    public HerbMutationRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, FluidStack fluidStack) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.fluidStack = fluidStack;
    }

    @Override
    public boolean matches(SimpleContainer pSimpleContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        // Check Input Slots
        for (int i = 0; i < inputItems.size(); i++) {
            ItemStack inputSlot = pSimpleContainer.getItem(i + 1);
            if (!inputItems.get(i).test(inputSlot)) {
                return false;
            }
        }

        return true;
    }

    public FluidStack getFluid() {
        return this.fluidStack;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
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

    public static class Type implements RecipeType<HerbMutationRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID =  "herb_mutation";
    }

    public static class Serializer implements RecipeSerializer<HerbMutationRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "herb_mutation");

        @Override
        public HerbMutationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            FluidStack fluid = FluidJSONUtil.readFluid(pSerializedRecipe.get("fluid").getAsJsonObject());
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();
            for (int i = 0; i < ingredients.size(); i++) {
                JsonObject ingredientJson = ingredients.get(i).getAsJsonObject();
                int count = GsonHelper.getAsInt(ingredientJson, "count", 1);
                ItemStack itemStack = Ingredient.fromJson(ingredientJson).getItems()[0];
                itemStack.setCount(count);
                inputs.add(Ingredient.of(itemStack));
            }
            return new HerbMutationRecipe(inputs, output, pRecipeId, fluid);
        }

        @Override
        public @Nullable HerbMutationRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            FluidStack fluid = pBuffer.readFluidStack();

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();

            return new HerbMutationRecipe(inputs, output, pRecipeId, fluid);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HerbMutationRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());
            pBuffer.writeFluidStack(pRecipe.fluidStack);

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
