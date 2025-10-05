package net.micaxs.smokeleaf.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HempHammer extends Item {
    public HempHammer(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }



    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        copy.setDamageValue(copy.getDamageValue() + 1);
        if (copy.getDamageValue() >= copy.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return copy;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) > 0) {
            stack.hurtAndBreak(1, miningEntity, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int max = stack.getMaxDamage();
        int used = stack.getDamageValue();
        int remaining = max - used;
        double ratio = max > 0 ? (double) remaining / max : 0.0;

        ChatFormatting color;
        if (ratio > 0.5) {
            color = ChatFormatting.GREEN;
        } else if (ratio > 0.25) {
            color = ChatFormatting.GOLD; // orange-ish
        } else {
            color = ChatFormatting.RED;
        }

        tooltipComponents.add(
                Component.translatable("tooltip.smokeleafindustries.hemp_hammer.uses", remaining, max)
                        .withStyle(color)
        );

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
