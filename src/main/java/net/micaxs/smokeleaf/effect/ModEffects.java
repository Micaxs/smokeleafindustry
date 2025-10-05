package net.micaxs.smokeleaf.effect;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.effect.beneficial.*;
import net.micaxs.smokeleaf.effect.harmful.*;
import net.micaxs.smokeleaf.effect.neutral.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, SmokeleafIndustries.MODID);


    public static final Holder<MobEffect> STONED = MOB_EFFECTS.register("stoned",
            () -> new StonedEffect(MobEffectCategory.NEUTRAL, 31458724));


    public static final Holder<MobEffect> SLEEPY = MOB_EFFECTS.register("sleepy",
            () -> new SleepyEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> RELAXED = MOB_EFFECTS.register("relaxed",
            () -> new RelaxedEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> EUPHORIC = MOB_EFFECTS.register("euphoric",
            () -> new EuphoricEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> UPLIFTED = MOB_EFFECTS.register("uplifted",
            () -> new UpliftedEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> RAINBOW = MOB_EFFECTS.register("rainbow",
            () -> new CreativeEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> BREATHING = MOB_EFFECTS.register("breathing",
            () -> new BreathingEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> BUBBLED = MOB_EFFECTS.register("bubbled",
            () -> new BubbledEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> MELTED = MOB_EFFECTS.register("melted",
            () -> new MeltedEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> AROUSED = MOB_EFFECTS.register("aroused",
            () -> new ArousedEffect(MobEffectCategory.BENEFICIAL, 31458724));
    public static final Holder<MobEffect> SHY = MOB_EFFECTS.register("shy",
            () -> new ShyEffect(MobEffectCategory.BENEFICIAL, 31458724)); // Invisibility
    public static final Holder<MobEffect> HIGH_FLYER = MOB_EFFECTS.register("high_flyer",
            () -> new HighFlyerEffect(MobEffectCategory.BENEFICIAL, 31458724)); // Invisibility


    public static final Holder<MobEffect> DRY_MOUTH = MOB_EFFECTS.register("dry_mouth",
            () -> new HeadSpinEffect(MobEffectCategory.HARMFUL, 31458724));
    public static final Holder<MobEffect> DIZZY = MOB_EFFECTS.register("dizzy",
            () -> new HeadSpinEffect(MobEffectCategory.HARMFUL, 31458724));
    public static final Holder<MobEffect> PARANOIA = MOB_EFFECTS.register("paranoia",
            () -> new ParanoiaEffect(MobEffectCategory.HARMFUL, 31458724));
    public static final Holder<MobEffect> DRY_EYES = MOB_EFFECTS.register("dry_eyes",
            () -> new RedEyesEffect(MobEffectCategory.HARMFUL, 31458724));
    public static final Holder<MobEffect> BRAIN_MELT = MOB_EFFECTS.register("brain_melt",
            () -> new BrainMeltEffect(MobEffectCategory.HARMFUL, 31458724));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}



/*
1. Breathing Screen Warp

The entire screen pulses in/out like it’s “breathing.”

Done by scaling the projection matrix slightly up and down (RenderLevelStageEvent).

Gives that “wavy” room feel.

2. Chromatic Aberration

Slightly offset red, green, and blue channels separately.

Can be faked by drawing the HUD/world three times with tiny offsets and tinting each pass.

Looks like a trippy 3D effect.

3. Rainbow Fog

Replace fog color with a constantly shifting rainbow (ViewportEvent.RenderFogColors).

Makes caves/fields glow psychedelic.

4. Swirling Particles

Spawn custom particles (tiny colored sparkles or spirals) around the player’s head.

Rotate them slowly in a circle.

5. Melty HUD

Make hotbar and hearts icons slowly slide left/right or “wiggle.”

Hook into RenderGuiEvent and offset elements with Math.sin(tickCount * 0.1).

6. Kaleidoscope Shader

Mirror the world render in quadrants or triangles.

A proper shader (GLSL) can be injected with ShaderInstance.

7. Fish-Eye Lens

Curve the world so the edges of the screen warp outward.

Can be approximated by tweaking FOV dynamically every few ticks.

8. Afterimages

Draw a faint ghostly “afterimage” of the player when they move/jump.

Achieved by rendering translucent entities with delay.

9. Fractal Stars

At night, add slowly rotating fractal/star patterns overlayed in the sky.

Hook into RenderLevelStageEvent → sky phase.

10. Sudden Color Flashes

Occasionally flash the screen with a bright neon overlay when mining/jumping.

Similar to a “synesthesia” effect.
 */