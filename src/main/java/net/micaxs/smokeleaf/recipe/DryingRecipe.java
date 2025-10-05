package net.micaxs.smokeleaf.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

import java.util.Optional;

/**
 * Drying recipe:
 * - ingredient: input item (bud, leaf, etc.)
 * - result: produced item (may be empty if only drying in-place, e.g. bud dry flag)
 * - time: ticks needed (default 200)
 * - dryBud: if true, block entity will set bud dry flag instead of replacing stack
 */
public record DryingRecipe(Ingredient ingredient, ItemStack result, int time, boolean dryBud)
        implements Recipe<DryingRecipeInput> {

    @Override
    public boolean matches(DryingRecipeInput input, Level level) {
        //if (level.isClientSide()) return false;
        return ingredient.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(DryingRecipeInput input, HolderLookup.Provider provider) {
        return result.copy();
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
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DRYING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DRYING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<DryingRecipe> {

        // JSON codec (result optional)
        public static final MapCodec<DryingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(DryingRecipe::ingredient),
                ItemStack.CODEC.optionalFieldOf("result", ItemStack.EMPTY)
                        .forGetter(r -> r.result().isEmpty() ? ItemStack.EMPTY : r.result()),
                Codec.INT.optionalFieldOf("time", 200).forGetter(DryingRecipe::time),
                Codec.BOOL.optionalFieldOf("dry_bud", false).forGetter(DryingRecipe::dryBud)
        ).apply(inst, (ing, stack, time, dryBud) -> new DryingRecipe(ing, stack, time, dryBud)));

        // Network codec (result optional -> avoid encoding empty with non-empty codec)
        public static final StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, DryingRecipe::ingredient,
                        ByteBufCodecs.optional(ItemStack.STREAM_CODEC),
                        r -> r.result().isEmpty() ? Optional.empty() : Optional.of(r.result()),
                        ByteBufCodecs.VAR_INT, DryingRecipe::time,
                        ByteBufCodecs.BOOL, DryingRecipe::dryBud,
                        (ing, optResult, time, dryBud) ->
                                new DryingRecipe(ing, optResult.orElse(ItemStack.EMPTY), time, dryBud)
                );

        @Override
        public MapCodec<DryingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}