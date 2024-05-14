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
import net.micaxs.smokeleafindustry.recipe.machines.HempWeaverRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class HempWeaverCategory implements IRecipeCategory<HempWeaverRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "hemp_weaver");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SmokeleafIndustryMod.MOD_ID,
            "textures/gui/hemp_weaver_gui.png");

    public final IDrawable background;
    public final IDrawable icon;

    public static final RecipeType<HempWeaverRecipe> HEMP_WEAVER_TYPE =
            new RecipeType<>(UID, HempWeaverRecipe.class);

    public HempWeaverCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 5 , 168, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.HEMP_WEAVER.get()));
    }

    @Override
    public RecipeType<HempWeaverRecipe> getRecipeType() {
        return HEMP_WEAVER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.smokeleafindustry.hemp_weaver");
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
    public void setRecipe(IRecipeLayoutBuilder builder, HempWeaverRecipe recipe, IFocusGroup focuses) {
        // Create a new ingredient with the correct count
        ItemStack ingredient = recipe.getIngredients().get(0).getItems()[0].copy();
        ingredient.setCount(recipe.getCount());

        // Add the ingredient to the recipe layout
        builder.addSlot(RecipeIngredientRole.INPUT, 75, 6).addIngredient(VanillaTypes.ITEM_STACK, ingredient);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 54).addItemStack(recipe.getResultItem(null));
    }
}
