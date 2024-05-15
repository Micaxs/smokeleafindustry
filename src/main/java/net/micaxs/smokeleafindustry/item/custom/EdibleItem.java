package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EdibleItem extends Item implements ContainsWeed {
    public static final float EDIBLE_FACTOR = 1.5F;

    public EdibleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 20;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        ItemStack original = player.getInventory().getSelected();
        if (!original.isEmpty() && original.getItem() instanceof EdibleItem) {
            CompoundTag originalTag = original.getTag();
            if (originalTag != null) {
                stack.setTag(originalTag.copy());
            }
        }
        super.onCraftedBy(stack, world, player);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        ItemStack mainHandItem = pLivingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        spawnSmokeParticles(pLevel, pLivingEntity);

        // Always get stoned from an edible
        int previousStonedDuration = 0;
        if (pLivingEntity.hasEffect(ModEffects.STONED.get())) {
            previousStonedDuration = pLivingEntity.getEffect(ModEffects.STONED.get()).getDuration();
        }
        pLivingEntity.addEffect(new MobEffectInstance(ModEffects.STONED.get(), previousStonedDuration + 200, 1));

        CompoundTag tag = mainHandItem.getTag();
        if (tag != null && tag.contains("effect") && tag.contains("duration")) {
            String effectId = tag.getString("effect");
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effectId));
            if (effect != null) {
                int duration = tag.getInt("duration");
                int previousEffectDuration = 0;
                if (pLivingEntity.hasEffect(effect)) {
                    previousEffectDuration = pLivingEntity.getEffect(effect).getDuration();
                }
                pLivingEntity.addEffect(new MobEffectInstance(effect, previousEffectDuration + duration, 1));
            }
        }

        mainHandItem.shrink(1);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void spawnSmokeParticles(Level level, LivingEntity entity) {
        for (int i = 0; i < 10; i++) {
            double xOffset = level.random.nextGaussian() * 0.02D;
            double yOffset = level.random.nextGaussian() * 0.02D;
            double zOffset = level.random.nextGaussian() * 0.02D;
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    entity.getX() + entity.getBbWidth() * (level.random.nextDouble() - 0.5D),
                    entity.getEyeY(),
                    entity.getZ() + entity.getBbWidth() * (level.random.nextDouble() - 0.5D),
                    xOffset, yOffset, zOffset);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.smokeleafindustry.effects").withStyle(ChatFormatting.GRAY));
        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("effect") && tag.contains("duration")) {
            String effectId = tag.getString("effect");
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effectId));
            if (effect != null) {
                MobEffectInstance effectInstance = new MobEffectInstance(effect, tag.getInt("duration"), 1);
                pTooltipComponents.add(getEffectText(effectInstance));
            }
        }
    }

    private Component getEffectText(MobEffectInstance effect) {
        MobEffect effectType = effect.getEffect();
        int duration = effect.getDuration() / 20;
        return Component.literal("- ")
                .append(Component.translatable(effectType.getDescriptionId()))
                .withStyle(ChatFormatting.GREEN)
                .append(" ")
                .append(Component.literal("(" + duration + "s)").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public float getEffectFactor() {
        return EDIBLE_FACTOR;
    }
}
