package net.micaxs.smokeleaf.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.micaxs.smokeleaf.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * Manual Grinder: single ingredient -> result, with per-recipe grind time (ticks).
 */
public record ManualGrinderRecipe(Ingredient ingredient, ItemStack result, int grindTime) implements Recipe<ManualGrinderInput> {

    @Override
    public boolean matches(ManualGrinderInput input, Level level) {
        return ingredient.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(ManualGrinderInput input, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MANUAL_GRINDER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MANUAL_GRINDER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ManualGrinderRecipe> {

        // Data (json) codec
        public static final MapCodec<ManualGrinderRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ManualGrinderRecipe::ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(ManualGrinderRecipe::result),
                Codec.INT.optionalFieldOf("grind_time", 40).forGetter(ManualGrinderRecipe::grindTime)
        ).apply(inst, ManualGrinderRecipe::new));

        // Network codec
        public static final StreamCodec<RegistryFriendlyByteBuf, ManualGrinderRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ManualGrinderRecipe::ingredient,
                        ItemStack.STREAM_CODEC, ManualGrinderRecipe::result,
                        ByteBufCodecs.VAR_INT, ManualGrinderRecipe::grindTime,
                        ManualGrinderRecipe::new
                );

        @Override
        public MapCodec<ManualGrinderRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ManualGrinderRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}