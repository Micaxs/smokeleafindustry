package net.micaxs.smokeleafindustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HempWeaverScreen extends AbstractContainerScreen<HempWeaverMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "textures/gui/hemp_weaver_gui.png");

    public HempWeaverScreen(HempWeaverMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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

        renderProgressArrow(guiGraphics, x, y);

        int energy = this.menu.getEnergyStoredScaled();
        guiGraphics.fill(x + 156, y + 11 + (64 - energy), x + 164, y + 75, 0xBFCC2222);

    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();
        Component energyText = Component.literal("Energy: " + energyStored + " / " + maxEnergy);
        if (isHovering(155, 11, 10, 65, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, energyText, mouseX, mouseY);
        }
    }
}
