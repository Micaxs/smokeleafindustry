package net.micaxs.smokeleafindustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class HerbMutationScreen extends AbstractContainerScreen<HerbMutationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "textures/gui/herb_mutation_gui.png");

    public HerbMutationScreen(HerbMutationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        guiGraphics.fill(x + 156, y + 13 + (64 - energy), x + 164, y + 77, 0xBFCC2222);

        FluidTank tank = this.menu.getBlockEntity().getFluidTank();
        FluidStack fluid = tank.getFluid();
        if (fluid.isEmpty()) return;

        int fluidHeight = getFluidHeight(tank);

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluid);
        if (stillTexture == null) return;


        TextureAtlasSprite sprite = this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluid);
        float alpha = ((tintColor >> 24) & 0xFF) / 255f;
        float red = ((tintColor >> 16) & 0xFF) / 255f;
        float green = ((tintColor >> 8) & 0xFF) / 255f;
        float blue = (tintColor & 0xFF) / 255f;

        guiGraphics.setColor(red, green, blue, alpha);

        GuiUtils.drawTiledSprite(guiGraphics, x + 55, y +15, 61, 16, fluidHeight, sprite, 16, 16, 0, GuiUtils.TilingDirection.DOWN_RIGHT);

        //guiGraphics.blit(x + 55,  getFluidY(fluidHeight, x, y), 0, 16, fluidHeight, sprite);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f );

    }

    private static int  getFluidHeight(IFluidTank tank) {
        return (int) (61 * ((float)tank.getFluidAmount() / tank.getCapacity()));
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 103, y + 37, 176, 0, 8, menu.getScaledProgress());
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

        FluidTank tank = this.menu.getBlockEntity().getFluidTank();
        FluidStack fluid = tank.getFluid();
        if (fluid.isEmpty()) return;

        if (isHovering(54, 15, 18, 63, mouseX, mouseY)) {
            Component fluidText = MutableComponent.create(fluid.getDisplayName().getContents())
                    .append(" (%s/%s mB)".formatted(tank.getFluidAmount(), tank.getCapacity()));
            guiGraphics.renderTooltip(this.font, fluidText, mouseX, mouseY);
        }

    }
}
