// java
package net.micaxs.smokeleaf.screen.custom;

import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.micaxs.smokeleaf.block.entity.BaseWeedCropBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MagnifyingGlassScreen extends Screen {
    private final BlockPos pos;

    public MagnifyingGlassScreen(BlockPos pos) {
        super(Component.literal("Plant Statistics"));
        this.pos = pos;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.addRenderableWidget(
                Button.builder(Component.literal("Close"), b -> onClose())
                        .bounds(centerX - 40, centerY + 70, 80, 20)
                        .build()
        );
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int x = this.width / 2;
        int y = this.height / 2 - 60;

        guiGraphics.drawCenteredString(this.font, this.title, x, y, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font,
                Component.literal("Position: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()),
                x, y + 14, 0xAAAAAA);

        Level level = Minecraft.getInstance().level;
        if (level == null) {
            guiGraphics.drawCenteredString(this.font, Component.literal("World not available."), x, y + 34, 0xFF5555);
            return;
        }

        // Resolve to the base block entity if the clicked block is the top half
        BlockPos bePos = pos;
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof BaseWeedCropBlock && state.getValue(BaseWeedCropBlock.TOP)) {
            bePos = pos.below();
        }

        BlockEntity be = level.getBlockEntity(bePos);
        if (be instanceof BaseWeedCropBlockEntity crop) {
            int line = 0;
            guiGraphics.drawCenteredString(this.font,
                    Component.literal("pH: " + crop.getPh()),
                    x, y + 34 + line++ * 12, 0xFFFFFF);

            guiGraphics.drawCenteredString(this.font,
                    Component.literal("N: " + crop.getNitrogen() + "  P: " + crop.getPhosphorus() + "  K: " + crop.getPotassium()),
                    x, y + 34 + line++ * 12, 0xFFFFFF);

            guiGraphics.drawCenteredString(this.font,
                    Component.literal("THC: " + crop.getThc() + "%  CBD: " + crop.getCbd() + "%"),
                    x, y + 34 + line * 12, 0xFFFFFF);
        } else {
            guiGraphics.drawCenteredString(this.font, Component.literal("No plant data available."), x, y + 34, 0xFF5555);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
