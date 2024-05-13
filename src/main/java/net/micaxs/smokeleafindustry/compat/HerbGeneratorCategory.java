package net.micaxs.smokeleafindustry.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.recipe.HerbGeneratorRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class HerbGeneratorCategory implements IRecipeCategory<HerbGeneratorRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "herb_generator");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SmokeleafIndustryMod.MOD_ID,
            "textures/gui/herb_generator_gui.png");

    public final IDrawable background;
    public final IDrawable icon;

    public static final RecipeType<HerbGeneratorRecipe> HERB_GENERATOR_TYPE =
            new RecipeType<>(UID, HerbGeneratorRecipe.class);

    public HerbGeneratorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 5, 168, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.HERB_GENERATOR.get()));
    }

    @Override
    public RecipeType<HerbGeneratorRecipe> getRecipeType() {
        return HERB_GENERATOR_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.smokeleafindustry.herb_generator");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, HerbGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 29).addIngredients(recipe.getIngredients().get(0));
        // TODO: add energy
    }
}
