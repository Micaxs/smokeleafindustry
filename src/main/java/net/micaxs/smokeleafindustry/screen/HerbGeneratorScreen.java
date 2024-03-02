package net.micaxs.smokeleafindustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HerbGeneratorScreen extends AbstractContainerScreen<HerbGeneratorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "textures/gui/herb_generator_gui.png");

    public HerbGeneratorScreen(HerbGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 1000000;
        this.titleLabelY = 1000000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderFuelProgress(guiGraphics, x, y);

        int energy = this.menu.getEnergyStoredScaled();
        // Background
        //guiGraphics.fill(x + 115, y + 20, x + 115, x + 131, y + 60, 0xFF555555);
        // Foreground
        guiGraphics.fill(x + 116, y+ 21 + (38 - energy), x + 129, y + 59, 0xBFCC2222);

    }

    private void renderFuelProgress(GuiGraphics guiGraphics, int x, int y) {
        int scaledHeight = menu.getFuelStoredScaled();
        if (scaledHeight > 0) {
            int adjustedY = y + 38 + (14 - scaledHeight);
            guiGraphics.blit(TEXTURE, x + 71, adjustedY, 176, 14 - scaledHeight, 14, scaledHeight);
        }
    }



    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

//        int fuelStored = this.menu.getFuel();
//        int burnTimeSeconds = (int) fuelStored / 20;
//
//        Component text = Component.literal("Fuel: " + burnTimeSeconds + "s");
//        if (isHovering(8, 35, 14, 14, mouseX, mouseY)) {
//            guiGraphics.renderTooltip(this.font, text, mouseX, mouseY);
//        }

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();
        Component energyText = Component.literal("Energy: " + energyStored + " / " + maxEnergy);
        if (isHovering(115, 20, 16, 40, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, energyText, mouseX, mouseY);
        }

    }
}
