package net.micaxs.smokeleaf.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder; // NEW
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.recipe.LiquifierRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import net.neoforged.neoforge.fluids.FluidUtil;

public class LiquifierRecipeCategory implements IRecipeCategory<LiquifierRecipe> {
    public static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "liquifier");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "textures/gui/liquifier/liquifier_gui.png");
    public static final RecipeType<LiquifierRecipe> LIQUIFIER_RECIPE_TYPE =
            new RecipeType<>(UID, LiquifierRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private static final int TANK_X = 129;
    private static final int TANK_Y = 6;
    private static final int TANK_WIDTH = 16;
    private static final int TANK_HEIGHT = 64;
    private static final int TANK_CAPACITY = 8000;

    // Move bucket to bottom-left next to the tank
    private static final int BUCKET_X = TANK_X - 20;              // left of tank with small padding
    private static final int BUCKET_Y = TANK_Y + TANK_HEIGHT - 16; // bottom-aligned with tank

    public LiquifierRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 5, 168, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.LIQUIFIER.get()));
    }

    @Override
    public RecipeType<LiquifierRecipe> getRecipeType() {
        return LIQUIFIER_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.smokeleafindustries.liquifier");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LiquifierRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 30)
                .addIngredients(recipe.getIngredients().getFirst());

        int capacityForRender = TANK_CAPACITY;
        builder.addSlot(RecipeIngredientRole.OUTPUT, TANK_X, TANK_Y)
                .setFluidRenderer(capacityForRender, false, TANK_WIDTH, TANK_HEIGHT)
                .addIngredient(NeoForgeTypes.FLUID_STACK, recipe.outputCopy());

        ItemStack filledBucket = FluidUtil.getFilledBucket(recipe.outputCopy());
        if (!filledBucket.isEmpty()) {
            IRecipeSlotBuilder bucket = builder.addSlot(RecipeIngredientRole.OUTPUT, BUCKET_X, BUCKET_Y)
                    .addItemStack(filledBucket);
            // Tooltip on hover over the bucket
            bucket.addRichTooltipCallback((slotView, tooltip) ->
                    tooltip.add(Component.translatable("jei.smokeleafindustries.bucket_use")));
        }
    }

    @Override
    public void draw(LiquifierRecipe recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }

    @Override
    public int getWidth() {
        return 168;
    }

    @Override
    public int getHeight() {
        return 75;
    }
}
