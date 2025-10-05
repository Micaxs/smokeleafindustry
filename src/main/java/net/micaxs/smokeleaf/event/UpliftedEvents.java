package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class UpliftedEvents {

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(ModEffects.UPLIFTED)) {
                Vec3 motion = player.getDeltaMovement();
                // Give extra upward momentum
                player.setDeltaMovement(motion.x, motion.y + 0.3D, motion.z);
            }
        }
    }
}
