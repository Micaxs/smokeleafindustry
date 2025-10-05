package net.micaxs.smokeleaf.item.custom;

import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.utils.WeedEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Random;

public class BaseWeedItem extends Item {

    private final MobEffect effect;
    private final int duration;
    private int effectAmplifier;
    private final int thcLevel;
    private final int cbdLevel;
    private boolean variableDuration;
    private final String[] weedNameParts = new String[2];
    private float durationMultiplier = 1;

    public BaseWeedItem(Properties pProperties, MobEffect effect, int iDuration, int iAmplifier, int iThc, int iCbd, String weedNamePart1, String weedNamePart2) {
        this(pProperties, effect, iDuration, iAmplifier, iThc, iCbd, true);
        this.weedNameParts[0] = weedNamePart1;
        this.weedNameParts[1] = weedNamePart2;
    }

    public BaseWeedItem(Properties pProperties, MobEffect effect, int iDuration, int iAmplifier, int iThc, int iCbd, boolean variableDuration) {
        super(pProperties);
        this.duration = iDuration;
        this.thcLevel = iThc;
        this.cbdLevel = iCbd;
        this.effect = effect;
        this.effectAmplifier = iAmplifier;
        this.variableDuration = variableDuration;
    }

    // Call this when creating a new ItemStack to sync fields to data components
    public void initializeStack(ItemStack stack) {
        stack.set(ModDataComponentTypes.ACTIVE_INGREDIENT.get(), BuiltInRegistries.MOB_EFFECT.getKey(this.effect).toString());
        stack.set(ModDataComponentTypes.EFFECT_DURATION.get(), this.duration);
        stack.set(ModDataComponentTypes.THC.get(), this.thcLevel);
        stack.set(ModDataComponentTypes.CBD.get(), this.cbdLevel);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        MobEffect eff = getEffect(stack);
        int dur = getDuration(stack);
        tooltipComponents.add(WeedEffectHelper.getEffectTooltip(eff, dur, !this.variableDuration));
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(getLevelsText(stack));

        if (this.variableDuration) {
            tooltipComponents.add(Component.translatable("tooltip.smokeleafindustries.base_weed").withStyle(ChatFormatting.GRAY));
        }
    }

    private Component getLevelsText(ItemStack stack) {
        int thc = getTHC(stack);
        int cbd = getCBD(stack);
        return Component.literal("Levels: ")
                .append(Component.literal(thc + "%").withStyle(ChatFormatting.GREEN))
                .append(Component.literal(" THC").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(" & ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(cbd + "%").withStyle(ChatFormatting.GREEN))
                .append(Component.literal(" CBD").withStyle(ChatFormatting.DARK_GRAY));
    }

    // Data component-aware accessors (fallback to fields for legacy)
    public int getDuration(ItemStack stack) {
        Integer baseDuration = stack.get(ModDataComponentTypes.EFFECT_DURATION.get());
        if (baseDuration == null) baseDuration = this.duration;
        float dur = baseDuration * this.durationMultiplier;
        if (this.variableDuration) {
            float randomDelta = new Random().nextFloat(Math.min(dur / 4, 200));
            return (int) (randomDelta + dur);
        }
        return (int) dur;
    }

    public MobEffect getEffect(ItemStack stack) {
        String effectId = stack.get(ModDataComponentTypes.ACTIVE_INGREDIENT.get());
        if (effectId == null) {
            effectId = BuiltInRegistries.MOB_EFFECT.getKey(this.effect).toString();
        }
        ResourceLocation effectRL = ResourceLocation.tryParse(effectId);
        if (effectRL != null) {
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(effectRL);
            if (effect != null) return effect;
        }
        return null;
    }

    public int getTHC(ItemStack stack) {
        Integer thc = stack.get(ModDataComponentTypes.THC.get());
        return thc != null ? thc : this.thcLevel;
    }

    public int getCBD(ItemStack stack) {
        Integer cbd = stack.get(ModDataComponentTypes.CBD.get());
        return cbd != null ? cbd : this.cbdLevel;
    }

    // Legacy field-based accessors for old code
    public int getDuration() { return this.duration; }
    public MobEffect getEffect() { return this.effect; }
    public int getEffectAmplifier() { return this.effectAmplifier; }
    public void setEffectAmplifier(int effectAmplifier) { this.effectAmplifier = effectAmplifier; }
    public boolean isVariableDuration() { return this.variableDuration; }
    public void setVariableDuration(boolean variableDuration) { this.variableDuration = variableDuration; }
    public String[] getWeedNameParts() { return weedNameParts; }
    public float getDurationMultiplier() { return durationMultiplier; }
    public void setDurationMultiplier(float durationMultiplier) { this.durationMultiplier = durationMultiplier; }
}
