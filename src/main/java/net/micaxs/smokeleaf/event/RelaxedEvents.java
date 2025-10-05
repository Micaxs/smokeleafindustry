package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class RelaxedEvents {

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(ModEffects.RELAXED)) {
            float tick = (player.tickCount % 360);
            event.setYaw(event.getYaw() + (float)Math.sin(tick * 0.01) * 1.5F);
            event.setPitch(event.getPitch() + (float)Math.cos(tick * 0.01) * 1.5F);
        }
    }

}
