// Java
package net.micaxs.smokeleaf.block.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.micaxs.smokeleaf.block.entity.GrowPotBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GrowPotRenderer implements BlockEntityRenderer<GrowPotBlockEntity> {
    private final BlockRenderDispatcher dispatcher;

    private static final float XZ_SCALE = 12f / 16f;
    private static final float Y_BASE = 1.0f;
    private static final float POT_HEIGHT = 8f / 16f;
    private static final float SOIL_HEIGHT = 3f / 16f;
    private static final float MAX_TOTAL_HEIGHT = 2.0f;
    private static final float MAX_CROP_HEIGHT = 2.0f;
    private static final float CROP_Y_SCALE = Math.max(0f, (MAX_TOTAL_HEIGHT - POT_HEIGHT - SOIL_HEIGHT) / MAX_CROP_HEIGHT);

    public GrowPotRenderer(BlockEntityRendererProvider.Context ctx) {
        this.dispatcher = Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(GrowPotBlockEntity be, float partialTicks, PoseStack pose,
                       MultiBufferSource buffers, int packedLight, int packedOverlay) {
        Level level = be.getLevel();
        if (level == null) return;

        BlockState soil = be.getSoilState();
        if (soil != null) {
            pose.pushPose();
            pose.translate(0.5D, 0.0D, 0.5D);
            pose.scale(XZ_SCALE, SOIL_HEIGHT, XZ_SCALE);
            pose.translate(-0.5D, 0.0D, -0.5D);
            pose.translate(0.0D, Y_BASE, 0.0D);
            dispatcher.renderSingleBlock(soil, pose, buffers, packedLight, OverlayTexture.NO_OVERLAY);
            pose.popPose();
        }

        BlockState cropBottom = be.getBottomCropStateForRender();
        if (cropBottom != null) {
            pose.pushPose();
            pose.translate(0.5D, 0.0D, 0.5D);
            pose.scale(XZ_SCALE, CROP_Y_SCALE, XZ_SCALE);
            pose.translate(-0.5D, 0.0D, -0.5D);
            pose.translate(0.0D, 0.6f, 0.0D);
            dispatcher.renderSingleBlock(cropBottom, pose, buffers, packedLight, OverlayTexture.NO_OVERLAY);

            BlockState cropTop = be.getTopCropStateForRender();
            if (cropTop != null) {
                pose.translate(0.0D, 1.0D, 0.0D);
                dispatcher.renderSingleBlock(cropTop, pose, buffers, packedLight, OverlayTexture.NO_OVERLAY);
            }
            pose.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(GrowPotBlockEntity be) {
        return true;
    }
}
