package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BongItem extends Item {
    public BongItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 30;
    }

    private List<MobEffectInstance> getOffhandEffects(ItemStack offhandItem, LivingEntity livingEntity) {
        List<MobEffectInstance> effects = new ArrayList<>();
        if (offhandItem.getItem() instanceof BaseWeedItem weedItem) {
            effects.addAll(weedItem.getEffects());
            List<MobEffectInstance> updatedEffects = new ArrayList<>();
            for (MobEffectInstance newEffect : new ArrayList<>(effects)) {
                boolean effectExists = false;
                for (MobEffectInstance existingEffect : livingEntity.getActiveEffects()) {
                    MobEffect existingMobEffect = existingEffect.getEffect();
                    MobEffect newMobEffect = newEffect.getEffect();
                    if (existingMobEffect.equals(newMobEffect)) {
                        effectExists = true;
                        int existingDuration = existingEffect.getDuration();
                        int newDuration = weedItem.getDuration() > 0 ? existingDuration + weedItem.getDuration() : 2410;
                        newDuration = Math.min(newDuration, 2410);
                        effects.remove(newEffect);
                        if (updatedEffects.stream().noneMatch(effect -> effect.getEffect().equals(newMobEffect))) {
                            updatedEffects.add(new MobEffectInstance(newMobEffect, newDuration, existingEffect.getAmplifier(), existingEffect.isAmbient(), existingEffect.isVisible()));
                        }
                        break;
                    }
                }
                if (!effectExists) {
                    if (updatedEffects.stream().noneMatch(effect -> effect.getEffect().equals(newEffect.getEffect()))) {
                        updatedEffects.add(new MobEffectInstance(newEffect.getEffect(), weedItem.getDuration() > 0 ? weedItem.getDuration() : 2410, newEffect.getAmplifier(), newEffect.isAmbient(), newEffect.isVisible()));
                    }
                }
            }
            effects.addAll(updatedEffects);
        }
        return effects;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        // Check if the offhand item is valid
        if (!isValidOffhandItem(pPlayer.getItemInHand(InteractionHand.OFF_HAND))) {
            return InteractionResultHolder.fail(itemstack);
        }

        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        InteractionHand offHand = InteractionHand.OFF_HAND;
        ItemStack offhandItem = pLivingEntity.getItemInHand(offHand);
        ItemStack mainhandItem = pLivingEntity.getItemInHand(InteractionHand.MAIN_HAND);

        if (isValidOffhandItem(offhandItem)) {
            pLivingEntity.setItemInHand(InteractionHand.OFF_HAND, offhandItem);

            List<MobEffectInstance> offhandEffects = getOffhandEffects(offhandItem, pLivingEntity);
            spawnSmokeParticles(pLevel, pLivingEntity);
            offhandItem.shrink(1);

            // Apply the offhand item's effects
            for (MobEffectInstance effect : offhandEffects) {
                pLivingEntity.addEffect(effect);
            }
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

    private boolean isValidOffhandItem(ItemStack offhand) {
        if (!offhand.isEmpty()) {
            return (offhand.getItem() instanceof BaseWeedItem);
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.bong").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public SoundEvent getEatingSound() {
        return ModSounds.BONG_HIT.get();
    }
}
