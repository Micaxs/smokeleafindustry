package net.micaxs.smokeleafindustry.effect;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<MobEffect> STONED = MOB_EFFECTS.register("stoned",
            () -> new StonedEffect(MobEffectCategory.NEUTRAL, 31458724));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
