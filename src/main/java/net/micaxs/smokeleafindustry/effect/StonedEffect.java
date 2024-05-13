package net.micaxs.smokeleafindustry.effect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class StonedEffect extends MobEffect {
    protected StonedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
            int duration = pLivingEntity.getEffect(this).getDuration();

            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, pAmplifier, true, true, false));
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, duration, pAmplifier, true, true, false));
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
