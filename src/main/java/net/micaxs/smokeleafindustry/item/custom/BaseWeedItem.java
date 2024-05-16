package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseWeedItem extends Item {
    private final MobEffect effect;
    private final int duration;
    private final int effectAmplifier;
    private final int thcLevel;
    private final int cbdLevel;
    private final boolean variableDuration;


    public BaseWeedItem(Properties pProperties, MobEffect effect, int iDuration, int iAmplifier, int iThc, int iCbd) {
        super(pProperties);
        this.duration = iDuration;
        this.thcLevel = iThc;
        this.cbdLevel = iCbd;
        this.effect = effect;
        this.effectAmplifier = iAmplifier;
        this.variableDuration = true;
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(WeedEffectHelper.getEffectTooltip(this.effect, this.duration, !this.variableDuration));
        pTooltipComponents.add(Component.empty());

        if (this.variableDuration) {
            // Variability message
            pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.base_weed").withStyle(ChatFormatting.GRAY));
        }

        // Disabled THC and CBD on tooltip since they aren't used, for now.
//        pTooltipComponents.add(Component.literal(" "));
//        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.levels").withStyle(ChatFormatting.GRAY));
//        pTooltipComponents.add(getLevelsText());
    }

    private Component getLevelsText() {
        return Component.literal(" ")
                .append(Component.literal("THC: ").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(thcLevel + "%").withStyle(ChatFormatting.GREEN))
                .append(Component.literal(" | ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal("CBD: ").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(cbdLevel + "%").withStyle(ChatFormatting.GREEN));
    }

    public int getDuration() {
        return this.duration;
    }

    public MobEffect getEffect() {
        return this.effect;
    }

    public int getEffectAmplifier() {
        return this.effectAmplifier;
    }

    public boolean isVariableDuration() {
        return this.variableDuration;
    }
}
