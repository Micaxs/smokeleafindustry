package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.DryingRackBlockEntity;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.item.custom.BaseBudItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum DryingRackProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "drying_rack");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof DryingRackBlockEntity rack)) return;

        for (int i = 0; i < DryingRackBlockEntity.SLOT_COUNT; i++) {
            ItemStack stack = rack.getItem(i);
            if (stack.isEmpty()) continue;

            boolean isBud = stack.getItem() instanceof BaseBudItem;
            boolean isDryBud = false;
            if (isBud) {
                Boolean dry = stack.get(ModDataComponentTypes.DRY);
                isDryBud = dry != null && dry;
            }

            int needed = rack.getTotalTimeForSlot(accessor.getLevel(), i);
            int prog = rack.getProgressForSlot(i);
            int remainTicks = 0;
            boolean active = false;

            if (isBud && isDryBud) {
                active = false;
                remainTicks = 0;
            } else if (needed > 0) {
                remainTicks = Math.max(0, needed - prog);
                active = remainTicks > 0;
            } else {
                active = false;
                remainTicks = 0;
            }

            int remainSeconds = (int) Math.ceil(remainTicks / 20.0);

            CompoundTag slot = new CompoundTag();
            slot.putString("id", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
            slot.putBoolean("bud", isBud);
            slot.putBoolean("dry", isDryBud);
            slot.putBoolean("active", active);
            slot.putInt("sec", remainSeconds);

            tag.put("S" + i, slot);
        }
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        IElementHelper elements = IElementHelper.get();

        for (int i = 0; i < DryingRackBlockEntity.SLOT_COUNT; i++) {
            String key = "S" + i;
            if (!accessor.getServerData().contains(key)) continue;

            CompoundTag s = accessor.getServerData().getCompound(key);
            ResourceLocation id = ResourceLocation.tryParse(s.getString("id"));
            if (id == null) continue;

            Item item = BuiltInRegistries.ITEM.get(id);
            IElement icon = elements.item(new ItemStack(item), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(-2, -1));

            boolean isBud = s.getBoolean("bud");
            boolean isDryBud = s.getBoolean("dry");
            boolean active = s.getBoolean("active");
            int seconds = s.getInt("sec");

            Component name = Component.translatable(item.getDescriptionId()).withStyle(ChatFormatting.WHITE);
            Component line;

            if (isBud && isDryBud) {
                line = Component.empty()
                        .append(name)
                        .append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal("Dry").withStyle(ChatFormatting.GREEN))
                        .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
            } else if (active) {
                String mmss = formatMMSS(seconds);
                line = Component.empty()
                        .append(name)
                        .append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal("Time Left: " + mmss).withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                line = name; // finished non-bud item; just the name (e.g., "Dried Tobacco Leaves")
            }

            // Icon + text on the same line
            tooltip.add(icon);
            tooltip.append(elements.text(line));
        }
    }

    private static String formatMMSS(int totalSeconds) {
        int m = Math.max(0, totalSeconds) / 60;
        int s = Math.max(0, totalSeconds) % 60;
        return m + ":" + (s < 10 ? "0" + s : String.valueOf(s));
    }
}
