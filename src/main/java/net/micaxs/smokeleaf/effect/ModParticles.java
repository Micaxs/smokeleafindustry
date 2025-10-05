package net.micaxs.smokeleaf.effect;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {

    // Must use ParticleType<?> as the registry generic
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, SmokeleafIndustries.MODID);

    // Two type arguments: <Registry base type, concrete instance type>
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRY_BUD_SPARK =
            PARTICLES.register("dry_bud_spark", () -> new SimpleParticleType(true));

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}