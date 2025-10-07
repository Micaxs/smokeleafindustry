package net.micaxs.smokeleaf.screen.custom;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.micaxs.smokeleaf.block.entity.BaseWeedCropBlockEntity;
import net.micaxs.smokeleaf.utils.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class MagnifyingGlassScreen extends Screen {
    private static final ResourceLocation BG_TEXTURE = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "textures/gui/analyzer/analyzer_gui.png"); // 256x256
    private static final int TEX_W = 256, TEX_H = 256;

    // GUI area to render from the texture (top-left 120x166) centered on screen
    private static final int GUI_W = 120, GUI_H = 166;

    // Nutrient bar constants (texture slices and on-GUI offsets)
    private static final int BAR_W = 100, BAR_H = 6;
    private static final int BAR_U = 0;
    private static final int BAR_V_N = 166;
    private static final int BAR_V_P = 172;
    private static final int BAR_V_K = 178;
    private static final int BAR_OFFSET_X = 10;
    private static final int BAR_OFFSET_Y_N = 51;
    private static final int BAR_OFFSET_Y_P = 72;
    private static final int BAR_OFFSET_Y_K = 93;
    private static final int MAX_NUTRIENT = 25; // cap at 25

    private static final int THC_LABEL_COLOR = 0x808080; // red-ish
    private static final int CBD_LABEL_COLOR = 0x808080; // green-ish
    private static final int VALUE_COLOR     = 0x87d33c; // yellow
    private static final int PERCENT_COLOR   = 0x808080; // gray


    private final BlockPos pos;

    public MagnifyingGlassScreen(BlockPos pos) {
        super(Component.literal("Plant Statistics"));
        this.pos = pos;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // Centered GUI placement
        int left = (this.width - GUI_W) / 2;
        int top = (this.height - GUI_H) / 2;

        // Draw the 120x166 GUI region from the top-left of the 256x256 texture
        guiGraphics.blit(BG_TEXTURE, left, top, 0, 0, GUI_W, GUI_H, TEX_W, TEX_H);

        // Title area
        int xCenter = this.width / 2;
        int yTopText = top + 17;

        // Resolve world and base-half pos before drawing the title
        Level level = Minecraft.getInstance().level;
        BlockPos bePos = this.pos;
        if (level != null) {
            BlockState stateAtPos = level.getBlockState(this.pos);
            if (stateAtPos.getBlock() instanceof BaseWeedCropBlock && stateAtPos.getValue(BaseWeedCropBlock.TOP)) {
                bePos = this.pos.below();
            }
        }

        // Dynamic title: plant block name (fallback to default title)
        Component titleToDraw = (level != null)
                ? level.getBlockState(bePos).getBlock().getName()
                : this.title;
        drawScaledCentered(guiGraphics, titleToDraw, xCenter, yTopText, 0xFFFFFF, 0.5f);



        if (level == null) {
            guiGraphics.drawCenteredString(this.font, Component.literal("World not available."), xCenter, yTopText + 34, 0xFF5555);
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_N, BAR_U, BAR_V_N, 0);
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_P, BAR_U, BAR_V_P, 0);
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_K, BAR_U, BAR_V_K, 0);
            return;
        }

        BlockEntity be = level.getBlockEntity(bePos);
        if (be instanceof BaseWeedCropBlockEntity crop) {
            int n = Mth.clamp(crop.getNitrogen(), 0, MAX_NUTRIENT);
            int p = Mth.clamp(crop.getPhosphorus(), 0, MAX_NUTRIENT);
            int k = Mth.clamp(crop.getPotassium(), 0, MAX_NUTRIENT);


            guiGraphics.drawString(this.font, Component.literal("Nitrogen (" + n + ")"), left + 25, top + 40, 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Phosphorus (" + p + ")"), left + 25, top + 61, 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Potassium (" + k + ")"), left + 25, top + 82, 0xFFFFFF);

//            float s = 0.5f;
//            drawScaled(guiGraphics, Component.literal("Nitrogen:"),    xCenter - 55, yTopText + 27, 0xFFFFFF, s);
//            drawScaled(guiGraphics, Component.literal("Phosphorus:"),  xCenter - 55, yTopText + 47, 0xFFFFFF, s);
//            drawScaled(guiGraphics, Component.literal("Potassium:"),   xCenter - 55, yTopText + 67, 0xFFFFFF, s);

//            guiGraphics.drawCenteredString(this.font, Component.literal("pH: " + crop.getPh()), xCenter, yTopText + 34 + line++ * 12, 0xFFFFFF);
//            guiGraphics.drawCenteredString(this.font, Component.literal("N: " + n + "  P: " + p + "  K: " + k), xCenter, yTopText + 34 + line++ * 12, 0xFFFFFF);
//            guiGraphics.drawCenteredString(this.font, Component.literal("THC: " + crop.getThc() + "%  CBD: " + crop.getCbd() + "%"), xCenter, yTopText + 34 + line * 12, 0xFFFFFF);


            if (crop.isValidNutrientsLevels()) {
                drawScaledCentered(guiGraphics, Component.literal("Levels Valid!"), xCenter, top + 110, 0x55FF55, 1f);
                drawStatsLine(guiGraphics, xCenter, top + 125, crop.getThc(), crop.getCbd(), 1f);
            } else {
                drawScaledCentered(guiGraphics, Component.literal("Levels Invalid!"), xCenter, top + 110, 0xFF5555, 1f);
                drawScaledCentered(guiGraphics, Component.literal("Plant produces no drops!"), xCenter, top + 125, 0x3d3d3d, 0.8f);
            }


            drawScaledCentered(guiGraphics, Component.literal("Press ESC to exit!"), xCenter, top + 150, 0x292929, 1f);

            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_N, BAR_U, BAR_V_N, scaledWidth(n));
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_P, BAR_U, BAR_V_P, scaledWidth(p));
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_K, BAR_U, BAR_V_K, scaledWidth(k));


            var optimalNutrients = crop.getOptimalNutrientsLevels();

            int barLeft  = left + BAR_OFFSET_X;
            int barRight = barLeft + BAR_W - 1; // inclusive


            // TODO: Render the still valid nutrient levels -1 and +1 as orange lines on the bars
            // TODO: Render the still valid nutrient levels -2 and +2 as red lines on the bars
            // TODO: Render the perfect valid nutrient levels as green lines on the bars
            if (optimalNutrients.n > 0 && optimalNutrients.n <= MAX_NUTRIENT) {
                int ox = Mth.clamp(barLeft + scaledWidth(optimalNutrients.n), barLeft, barRight);
                guiGraphics.fill(ox, top + BAR_OFFSET_Y_N, ox + 1, top + BAR_OFFSET_Y_N + BAR_H, 0xFFFFFFFF);
            }
            if (optimalNutrients.p > 0 && optimalNutrients.p <= MAX_NUTRIENT) {
                int ox = Mth.clamp(barLeft + scaledWidth(optimalNutrients.p), barLeft, barRight);
                guiGraphics.fill(ox, top + BAR_OFFSET_Y_P, ox + 1, top + BAR_OFFSET_Y_P + BAR_H, 0xFFFFFFFF);
            }
            if (optimalNutrients.k > 0 && optimalNutrients.k <= MAX_NUTRIENT) {
                int ox = Mth.clamp(barLeft + scaledWidth(optimalNutrients.k), barLeft, barRight);
                guiGraphics.fill(ox, top + BAR_OFFSET_Y_K, ox + 1, top + BAR_OFFSET_Y_K + BAR_H, 0xFFFFFFFF);
            }

            renderOptimalNutrientsTooltip(
                    guiGraphics, mouseX, mouseY, left, top,
                    n, p, k,
                    optimalNutrients.n, optimalNutrients.p, optimalNutrients.k
            );

        } else {
            guiGraphics.drawCenteredString(this.font, Component.literal("No plant data available."), xCenter, yTopText + 34, 0xFF5555);
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_N, BAR_U, BAR_V_N, 0);
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_P, BAR_U, BAR_V_P, 0);
            drawBar(guiGraphics, left + BAR_OFFSET_X, top + BAR_OFFSET_Y_K, BAR_U, BAR_V_K, 0);
        }
    }

    private void renderOptimalNutrientsTooltip(GuiGraphics g,
                                               int mouseX, int mouseY,
                                               int baseX, int baseY,
                                               int n, int p, int k,
                                               int optN, int optP, int optK) {
        // N bar
        if (isMouseAboveArea(mouseX, mouseY, baseX, baseY, BAR_OFFSET_X, BAR_OFFSET_Y_N, BAR_W, BAR_H)) {
            g.renderTooltip(
                    this.font,
                    List.of(
                            Component.literal("Nitrogen"),
                            Component.literal("Current: " + n),
                            Component.literal("Optimal: " + optN)
                    ),
                    Optional.empty(),
                    mouseX, mouseY
            );
            return;
        }
        // P bar
        if (isMouseAboveArea(mouseX, mouseY, baseX, baseY, BAR_OFFSET_X, BAR_OFFSET_Y_P, BAR_W, BAR_H)) {
            g.renderTooltip(
                    this.font,
                    List.of(
                            Component.literal("Phosphorus"),
                            Component.literal("Current: " + p),
                            Component.literal("Optimal: " + optP)
                    ),
                    Optional.empty(),
                    mouseX, mouseY
            );
            return;
        }
        // K bar
        if (isMouseAboveArea(mouseX, mouseY, baseX, baseY, BAR_OFFSET_X, BAR_OFFSET_Y_K, BAR_W, BAR_H)) {
            g.renderTooltip(
                    this.font,
                    List.of(
                            Component.literal("Potassium"),
                            Component.literal("Current: " + k),
                            Component.literal("Optimal: " + optK)
                    ),
                    Optional.empty(),
                    mouseX, mouseY
            );
        }
    }


    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }


    private void drawStatsLine(GuiGraphics g, int centerX, int y, int thc, int cbd, float scale) {
        String sThcLabel = "THC: ";
        String sThcVal   = String.valueOf(thc);
        String sPct      = "%";
        String sSpacer   = "  ";
        String sCbdLabel = "CBD: ";
        String sCbdVal   = String.valueOf(cbd);

        int wThcLabel = this.font.width(sThcLabel);
        int wThcVal   = this.font.width(sThcVal);
        int wPct      = this.font.width(sPct);
        int wSpacer   = this.font.width(sSpacer);
        int wCbdLabel = this.font.width(sCbdLabel);
        int wCbdVal   = this.font.width(sCbdVal);

        float totalUnscaled = wThcLabel + wThcVal + wPct + wSpacer + wCbdLabel + wCbdVal + wPct;
        int left = (int) (centerX - (totalUnscaled * scale) / 2f);

        g.pose().pushPose();
        g.pose().translate(left, y, 0);
        g.pose().scale(scale, scale, 1f);

        int x = 0;
        g.drawString(this.font, sThcLabel, x, 0, THC_LABEL_COLOR, false); x += wThcLabel;
        g.drawString(this.font, sThcVal,   x, 0, VALUE_COLOR,     false); x += wThcVal;
        g.drawString(this.font, sPct,      x, 0, PERCENT_COLOR,   false); x += wPct;
        x += wSpacer; // skip drawing the spacer, just advance

        g.drawString(this.font, sCbdLabel, x, 0, CBD_LABEL_COLOR, false); x += wCbdLabel;
        g.drawString(this.font, sCbdVal,   x, 0, VALUE_COLOR,     false); x += wCbdVal;
        g.drawString(this.font, sPct,      x, 0, PERCENT_COLOR,   false);

        g.pose().popPose();
    }


    private int scaledWidth(int value) {
        int v = Mth.clamp(value, 0, MAX_NUTRIENT);
        return (int) Math.round((v / (double) MAX_NUTRIENT) * BAR_W);
    }

    // Blits a BAR_W x BAR_H slice but only 'width' pixels wide (progress)
    private void drawBar(GuiGraphics g, int x, int y, int u, int v, int width) {
        if (width <= 0) return;
        g.blit(BG_TEXTURE, x, y, u, v, width, BAR_H, TEX_W, TEX_H);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }



    private void drawScaled(GuiGraphics g, Component text, int x, int y, int color, float scale) {
        g.pose().pushPose();
        g.pose().translate(x, y, 0);
        g.pose().scale(scale, scale, 1.0f);
        g.drawString(this.font, text, 0, 0, color, false);
        g.pose().popPose();
    }

    private void drawScaledCentered(GuiGraphics g, Component text, int centerX, int y, int color, float scale) {
        int w = this.font.width(text);
        int left = (int) (centerX - (w * scale) / 2.0f);
        g.pose().pushPose();
        g.pose().translate(left, y, 0);
        g.pose().scale(scale, scale, 1.0f);
        g.drawString(this.font, text, 0, 0, color, false);
        g.pose().popPose();
    }
}
