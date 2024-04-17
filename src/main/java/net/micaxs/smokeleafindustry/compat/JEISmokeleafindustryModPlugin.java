package net.micaxs.smokeleafindustry.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.recipe.HempSpinnerRecipe;
import net.micaxs.smokeleafindustry.recipe.HerbEvaporatorRecipe;
import net.micaxs.smokeleafindustry.recipe.HerbGrinderRecipe;
import net.micaxs.smokeleafindustry.recipe.HerbMutationRecipe;
import net.micaxs.smokeleafindustry.screen.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
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
        registration.addRecipeCategories(new HerbMutationCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HerbEvaporatorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HempSpinnerCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<HerbGrinderRecipe> grinderRecipes = recipeManager.getAllRecipesFor(HerbGrinderRecipe.Type.INSTANCE);
        registration.addRecipes(HerbGrinderCategory.HERB_GRINDER_TYPE, grinderRecipes);

        List<HerbMutationRecipe> mutationRecipes = recipeManager.getAllRecipesFor(HerbMutationRecipe.Type.INSTANCE);
        registration.addRecipes(HerbMutationCategory.HERB_MUTATION_TYPE, mutationRecipes);

        List<HerbEvaporatorRecipe> evaporatorRecipes = recipeManager.getAllRecipesFor(HerbEvaporatorRecipe.Type.INSTANCE);
        registration.addRecipes(HerbEvaporatorCategory.HERB_EVAPORATOR_TYPE, evaporatorRecipes);

        List<HempSpinnerRecipe> spinnerRecipes = recipeManager.getAllRecipesFor(HempSpinnerRecipe.Type.INSTANCE);
        registration.addRecipes(HempSpinnerCategory.HEMP_SPINNER_TYPE, spinnerRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(HerbGrinderStationScreen.class, 80, 30, 20, 30,
                HerbGrinderCategory.HERB_GRINDER_TYPE);

        registration.addRecipeClickArea(HempSpinnerScreen.class, 80, 30, 20, 30,
                HempSpinnerCategory.HEMP_SPINNER_TYPE);

        registration.addRecipeClickArea(HerbMutationScreen.class, 80, 30, 20, 30,
                HerbMutationCategory.HERB_MUTATION_TYPE);

        registration.addRecipeClickArea(HerbEvaporatorScreen.class, 80, 30, 20, 30,
                HerbEvaporatorCategory.HERB_EVAPORATOR_TYPE);
    }
}
