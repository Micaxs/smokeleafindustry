package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BluntItem extends Item {
    public BluntItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 20;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    // TODO: Fix / check if item in offhand or mainhand and deduct as needed
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        ItemStack mainhandItem = pLivingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        mainhandItem.shrink(1);
        spawnSmokeParticles(pLevel, pLivingEntity);

        // Give stoned effect
        if (!pLevel.isClientSide()) {
            pLivingEntity.addEffect(new MobEffectInstance(ModEffects.STONED.get(), 200, 1));
        }


        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void spawnSmokeParticles(Level level, LivingEntity entity) {
        // Adjust particle spawning based on your preferences
        for (int i = 0; i < 10; i++) {
            double xOffset = level.random.nextGaussian() * 0.02D;
            double yOffset = level.random.nextGaussian() * 0.02D;
            double zOffset = level.random.nextGaussian() * 0.02D;

            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    entity.getX() + entity.getBbWidth() * (level.random.nextDouble() - 0.5D),
                    entity.getEyeY(),
                    entity.getZ() + entity.getBbWidth() * (level.random.nextDouble() - 0.5D),
                    xOffset, yOffset, zOffset);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.blunt").withStyle(ChatFormatting.GRAY));
    }
}
