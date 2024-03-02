package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.item.custom.weeds.BubbleKushWeedItem;
import net.micaxs.smokeleafindustry.item.custom.weeds.WhiteWidowWeedItem;
import net.micaxs.smokeleafindustry.sound.ModSounds;
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
        return 40;
    }

    private List<MobEffectInstance> getOffhandEffects(ItemStack offhandItem) {
        List<MobEffectInstance> effects = new ArrayList<>();

        // TODO: Add more weed item effects here when created
        if (offhandItem.getItem() instanceof WhiteWidowWeedItem) {
            effects.addAll(((WhiteWidowWeedItem) offhandItem.getItem()).getEffects());
        } else if (offhandItem.getItem() instanceof BubbleKushWeedItem) {
            effects.addAll(((BubbleKushWeedItem) offhandItem.getItem()).getEffects());
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
            offhandItem.shrink(1);
            pLivingEntity.setItemInHand(InteractionHand.OFF_HAND, offhandItem);

            List<MobEffectInstance> offhandEffects = getOffhandEffects(offhandItem);

            spawnSmokeParticles(pLevel, pLivingEntity);

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
        // TODO: Add more weed item effects here when created

        if (!offhand.isEmpty()) {
            return  offhand.getItem() == ModItems.WHITE_WIDOW_WEED.get() ||
                    offhand.getItem() == ModItems.BUBBLE_KUSH_WEED.get();
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
