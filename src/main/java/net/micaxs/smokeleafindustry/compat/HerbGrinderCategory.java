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
import net.micaxs.smokeleafindustry.recipe.machines.HerbGrinderRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class HerbGrinderCategory implements IRecipeCategory<HerbGrinderRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "herb_grinder");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SmokeleafIndustryMod.MOD_ID,
            "textures/gui/herb_grinder_station_gui.png");

    public final IDrawable background;
    public final IDrawable icon;

    public static final RecipeType<HerbGrinderRecipe> HERB_GRINDER_TYPE =
            new RecipeType<>(UID, HerbGrinderRecipe.class);

    public HerbGrinderCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 5 , 168, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.HERB_GRINDER_STATION.get()));
    }

    @Override
    public RecipeType<HerbGrinderRecipe> getRecipeType() {
        return HERB_GRINDER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.smokeleafindustry.herb_grinder_station");
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
    public void setRecipe(IRecipeLayoutBuilder builder, HerbGrinderRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 75, 6).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 54).addItemStack(recipe.getResultItem(null));
    }
}
