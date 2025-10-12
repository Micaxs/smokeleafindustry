package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.micaxs.smokeleaf.block.entity.BaseWeedCropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import net.minecraft.nbt.CompoundTag;

public enum WeedCropProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "weed_crop_jei");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    // Server -> client data
    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        BlockState state = accessor.getBlockState();
        BlockPos pos = accessor.getPosition();

        // Always grab the bottom block as that's the Entity.
        if (state.hasProperty(BaseWeedCropBlock.TOP) && state.getValue(BaseWeedCropBlock.TOP)) {
            pos = pos.below();
        }

        BlockEntity be = accessor.getLevel().getBlockEntity(pos);
        if (be instanceof BaseWeedCropBlockEntity crop) {
            tag.putInt("n", crop.getNitrogen());
            tag.putInt("p", crop.getPhosphorus());
            tag.putInt("k", crop.getPotassium());
        }
    }

    // Client tooltip
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains("n")) return;

        int n = data.getInt("n");
        int p = data.getInt("p");
        int k = data.getInt("k");

        tooltip.add(Component.literal("Nitrogen (N): " + n));
        tooltip.add(Component.literal("Phosphorus (P): " + p));
        tooltip.add(Component.literal("Potassium (K): " + k));
    }
}
