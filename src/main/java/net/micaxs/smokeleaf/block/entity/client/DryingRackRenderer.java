package net.micaxs.smokeleaf.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.micaxs.smokeleaf.block.entity.DryingRackBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DryingRackRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {

    // Slight downward adjustment
    private static final float Y_OFFSET_ADJUST = 0.05f;

    public DryingRackRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(DryingRackBlockEntity be, float partialTicks, PoseStack pose,
                       MultiBufferSource buffers, int light, int overlay) {
        if (be.getLevel() == null) return;
        var state = be.getBlockState();
        var facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

        for (int slot = 0; slot < DryingRackBlockEntity.SLOT_COUNT; slot++) {
            ItemStack stack = be.getItem(slot);
            if (stack.isEmpty()) continue;

            float[] off = be.getSlotRenderPosition(slot, facing);
            pose.pushPose();
            // Center + offset (lowered slightly)
            pose.translate(0.5 + off[0], off[1] - Y_OFFSET_ADJUST, 0.5 + off[2]);
            // Lay flat
            pose.mulPose(Axis.XP.rotationDegrees(90f));
            // Scale
            pose.scale(0.4f, 0.4f, 0.4f);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    ItemDisplayContext.FIXED,
                    light,
                    overlay,
                    pose,
                    buffers,
                    be.getLevel(),
                    0
            );
            pose.popPose();
        }
    }
}