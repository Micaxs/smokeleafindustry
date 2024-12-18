package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.sound.ModSounds;
import net.micaxs.smokeleafindustry.utils.HashOilHelper;
import net.micaxs.smokeleafindustry.utils.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
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

import java.util.ArrayList;
import java.util.List;

public class DabRigItem extends Item {
    public DabRigItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 30;
    }

    private List<MobEffectInstance> getOffhandEffects(ItemStack offhandItem, LivingEntity livingEntity) {
        List<MobEffectInstance> offHandEffectList = new ArrayList<>();

        if (offhandItem.getItem() instanceof BaseWeedItem weedItem) {
            if (livingEntity.hasEffect(weedItem.getEffect())) {
                int previousEffectDuration = livingEntity.getEffect(weedItem.getEffect()).getDuration();
                offHandEffectList.add(new MobEffectInstance(weedItem.getEffect(), previousEffectDuration + weedItem.getDuration(),
                        weedItem.getEffectAmplifier()));
            } else {
                offHandEffectList.add(new MobEffectInstance(weedItem.getEffect(), weedItem.getDuration(),
                        weedItem.getEffectAmplifier()));
            }
        } else if (offhandItem.getItem() instanceof HashOilTinctureItem hashOilTincture) {
            List<BaseWeedItem> weedItems = HashOilHelper.getActiveWeedIngredient(hashOilTincture.getShareTag(offhandItem));
            for (BaseWeedItem weedItem: weedItems) {
                if (livingEntity.hasEffect(weedItem.getEffect())) {
                    int previousEffectDuration = livingEntity.getEffect(weedItem.getEffect()).getDuration();
                    offHandEffectList.add(new MobEffectInstance(weedItem.getEffect(), previousEffectDuration + weedItem.getDuration(),
                            weedItem.getEffectAmplifier()));
                } else {
                    offHandEffectList.add(new MobEffectInstance(weedItem.getEffect(), weedItem.getDuration(), weedItem.getEffectAmplifier()));
                }
            }
        }

        return offHandEffectList;
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

        if (isValidOffhandItem(offhandItem)) {
            pLivingEntity.setItemInHand(InteractionHand.OFF_HAND, offhandItem);

            List<MobEffectInstance> offhandEffects = getOffhandEffects(offhandItem, pLivingEntity);
            spawnSmokeParticles(pLevel, pLivingEntity);

            if (offhandItem.is(ModItems.HASH_OIL_TINCTURE.get())) {
                Player pPlayer = (Player) pLivingEntity;
                if(offhandItem.hurt(1, RandomSource.create(), null)) {
                    pPlayer.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.EMPTY_TINCTURE.get()));
                }
            } else {
                offhandItem.shrink(1);
            }

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
            return offhand.is(ModTags.WEED_EXTRACTS) || offhand.is(ModItems.HASH_OIL_TINCTURE.get());
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.dab_rig").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return ModSounds.BONG_HIT.get();
    }
}
