// Java
package net.micaxs.smokeleaf.client;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.client.particle.EchoLocationParticle;
import net.micaxs.smokeleaf.effect.ModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID, value = Dist.CLIENT)
public class ModParticleFactories {

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent evt) {
        evt.registerSpriteSet(ModParticles.ECHO_LOCATION_PARTICLE.get(), EchoLocationParticle.Provider::new);
    }
}
