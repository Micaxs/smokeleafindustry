package net.micaxs.smokeleafindustry.item.custom;

import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseWeedItem extends Item {

    private final List<MobEffectInstance> effects = new ArrayList<>();
    public int duration;

    public int THC_LEVEL = 50;
    public int CBD_LEVEL = 50;

    public BaseWeedItem(Properties pProperties, MobEffect effect, int iDuration, int iAmplifier, int iThc, int iCbd) {
        super(pProperties);
        duration = iDuration;
        THC_LEVEL = iThc;
        CBD_LEVEL = iCbd;
        effects.add(new MobEffectInstance(effect, iDuration, iAmplifier));
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.effects").withStyle(ChatFormatting.GRAY));
        List<MobEffectInstance> weedEffects = getEffects();
        for (MobEffectInstance effect : weedEffects) {
            pTooltipComponents.add(getEffectText(effect));
        }
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.levels").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(getLevelsText());
    }

    private Component getEffectText(MobEffectInstance effect) {
        MobEffect effectType = effect.getEffect();
        int duration = effect.getDuration() / 20; // Convert duration to seconds

        // Display effect name and duration in tooltip
        return Component.literal(" ")
                .append(Component.translatable(effectType.getDescriptionId()))
                .withStyle(ChatFormatting.GREEN)
                .append(" ")
                .append(Component.literal("(" + duration + "s)").withStyle(ChatFormatting.GRAY));
    }

    private Component getLevelsText() {
        return Component.literal(" ")
                .append(Component.literal("THC: ").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(THC_LEVEL + "%").withStyle(ChatFormatting.GREEN))
                .append(Component.literal(" | ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal("CDB: ").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(CBD_LEVEL + "%").withStyle(ChatFormatting.GREEN));
    }


    public int getDuration() {
        return duration;
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = super.getShareTag(stack);
        if (tag == null) {
            tag = new CompoundTag();
        }

        // Set the THC and CBD levels into the tag
        tag.putInt("thc", THC_LEVEL);
        tag.putInt("cbd", CBD_LEVEL);

        // Set the effect based on the actual effect of the BaseWeedItem
        if (!effects.isEmpty()) {
            MobEffect effect = effects.get(0).getEffect();
            ResourceLocation effectRegistryName = ForgeRegistries.MOB_EFFECTS.getKey(effect);
            if (effectRegistryName != null) {
                tag.putString("effect", effectRegistryName.toString());
            }
        }
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null && nbt.contains("effects")) {
            effects.clear();
            ListTag effectsList = nbt.getList("effects", 10); // 10 is the NBT type for CompoundTag
            for (int i = 0; i < effectsList.size(); i++) {
                effects.add(MobEffectInstance.load(effectsList.getCompound(i)));
            }
        }

        // Read THC and CBD Levels from tag
        if (nbt != null && nbt.contains("thc")) {
            THC_LEVEL = nbt.getInt("thc");
        }
        if (nbt != null && nbt.contains("cbd")) {
            CBD_LEVEL = nbt.getInt("cbd");
        }
    }
}