package net.micaxs.smokeleafindustry.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.recipe.HerbEvaporatorRecipe;
import net.micaxs.smokeleafindustry.recipe.HerbExtractorRecipe;
import net.micaxs.smokeleafindustry.recipe.HerbGrinderRecipe;
import net.micaxs.smokeleafindustry.recipe.HerbMutationRecipe;
import net.micaxs.smokeleafindustry.screen.HerbEvaporatorScreen;
import net.micaxs.smokeleafindustry.screen.HerbExtractorScreen;
import net.micaxs.smokeleafindustry.screen.HerbGrinderStationScreen;
import net.micaxs.smokeleafindustry.screen.HerbMutationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEISmokeleafindustryModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new HerbGrinderCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HerbExtractorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HerbMutationCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HerbEvaporatorCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<HerbGrinderRecipe> grinderRecipes = recipeManager.getAllRecipesFor(HerbGrinderRecipe.Type.INSTANCE);
        registration.addRecipes(HerbGrinderCategory.HERB_GRINDER_TYPE, grinderRecipes);

        List<HerbExtractorRecipe> extractorRecipes = recipeManager.getAllRecipesFor(HerbExtractorRecipe.Type.INSTANCE);
        registration.addRecipes(HerbExtractorCategory.HERB_EXTRACTOR_TYPE, extractorRecipes);

        List<HerbMutationRecipe> mutationRecipes = recipeManager.getAllRecipesFor(HerbMutationRecipe.Type.INSTANCE);
        registration.addRecipes(HerbMutationCategory.HERB_MUTATION_TYPE, mutationRecipes);

        List<HerbEvaporatorRecipe> evaporatorRecipes = recipeManager.getAllRecipesFor(HerbEvaporatorRecipe.Type.INSTANCE);
        registration.addRecipes(HerbEvaporatorCategory.HERB_EVAPORATOR_TYPE, evaporatorRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(HerbGrinderStationScreen.class, 80, 30, 20, 30,
                HerbGrinderCategory.HERB_GRINDER_TYPE);

        registration.addRecipeClickArea(HerbExtractorScreen.class, 80, 30, 20, 30,
                HerbExtractorCategory.HERB_EXTRACTOR_TYPE);

        registration.addRecipeClickArea(HerbMutationScreen.class, 80, 30, 20, 30,
                HerbMutationCategory.HERB_MUTATION_TYPE);

        registration.addRecipeClickArea(HerbEvaporatorScreen.class, 80, 30, 20, 30,
                HerbEvaporatorCategory.HERB_EVAPORATOR_TYPE);
    }
}
