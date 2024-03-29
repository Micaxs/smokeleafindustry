package net.micaxs.smokeleafindustry.recipe;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static  final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<HerbGrinderRecipe>> HERB_GRINDER_SERIALIZER =
            SERIALIZERS.register("herb_grinder", () -> HerbGrinderRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<HerbExtractorRecipe>> HERB_EXTRACTOR_SERIALIZER =
            SERIALIZERS.register("herb_extractor", () -> HerbExtractorRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<HerbMutationRecipe>> HERB_MUTATION_SERIALIZER =
            SERIALIZERS.register("herb_mutation", () -> HerbMutationRecipe.Serializer.INSTANCE);
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}
