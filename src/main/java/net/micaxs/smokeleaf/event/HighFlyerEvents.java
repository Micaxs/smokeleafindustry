package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class HighFlyerEvents {

    @SubscribeEvent
    public static void onAdded(MobEffectEvent.Added event) {
        MobEffectInstance inst = event.getEffectInstance();
        if (inst == null || inst.getEffect() != ModEffects.HIGH_FLYER) return;

        if (event.getEntity() instanceof ServerPlayer sp) {
            // Immediately allow flight
            sp.getAbilities().mayfly = true;
            sp.onUpdateAbilities();
        }
    }

    @SubscribeEvent
    public static void onExpired(MobEffectEvent.Expired event) {
        handleEnd(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onRemoved(MobEffectEvent.Remove event) {
        handleEnd(event.getEntity(), event.getEffectInstance());
    }

    private static void handleEnd(LivingEntity entity, MobEffectInstance inst) {
        if (inst == null || inst.getEffect() != ModEffects.HIGH_FLYER) return;
        if (!(entity instanceof ServerPlayer sp)) return;

        // If no other source keeps flight, remove it
        AttributeInstance flightAttr = sp.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        double value = flightAttr != null ? flightAttr.getValue() : 0.0D;

        if (value <= 0.0D && !sp.getAbilities().instabuild) {
            sp.getAbilities().mayfly = false;
            sp.getAbilities().flying = false;
            sp.onUpdateAbilities();
        }
    }
}