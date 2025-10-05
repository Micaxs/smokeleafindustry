package net.micaxs.smokeleaf.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class StonerSightEffect extends MobEffect {

    public StonerSightEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide() && entity instanceof Player player) {
            String playerTag = "stonersight:" + player.getUUID();

            // Find all living entities within 32 blocks
            List<LivingEntity> nearby = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(32.0D),
                    e -> e != player && e.isAlive()
            );

            for (LivingEntity mob : nearby) {
                if (!mob.getTags().contains(playerTag)) {
                    mob.addTag(playerTag);
                }
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4, 0, false, false, false));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true; // run every tick
    }
}