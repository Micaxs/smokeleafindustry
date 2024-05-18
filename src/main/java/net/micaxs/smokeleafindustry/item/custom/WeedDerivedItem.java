package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.effect.ModEffects;
import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
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
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeedDerivedItem extends Item {
    private final float effectDurationMultiplier;
    private final float stonedChance;
    private final UseAnim useAnimation;

    public WeedDerivedItem(Properties pProperties, float effectDurationMultiplier, float stonedChance, UseAnim useAnimation) {
        super(pProperties);
        this.effectDurationMultiplier = effectDurationMultiplier;
        this.stonedChance = stonedChance;
        this.useAnimation = useAnimation;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return this.useAnimation;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 20;
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

        // A stonedChance of 1 will always result in being stoned
        if (pLevel.random.nextDouble() <= this.stonedChance) {
            int previousStonedDuration = 0;
            if (pLivingEntity.hasEffect(ModEffects.STONED.get())) {
                previousStonedDuration = pLivingEntity.getEffect(ModEffects.STONED.get()).getDuration();
            }
            pLivingEntity.addEffect(new MobEffectInstance(ModEffects.STONED.get(), previousStonedDuration + 200, 1));
        }

        CompoundTag tag = mainHandItem.getTag();
        BaseWeedItem activeWeedIngredient = getActiveWeedIngredient(pStack);
        if (tag != null && tag.contains("duration") && activeWeedIngredient != null) {

            int duration = tag.getInt("duration");
            int previousEffectDuration = 0;
            if (pLivingEntity.hasEffect(activeWeedIngredient.getEffect())) {
                previousEffectDuration = pLivingEntity.getEffect(activeWeedIngredient.getEffect()).getDuration();
            }
            pLivingEntity.addEffect(new MobEffectInstance(activeWeedIngredient.getEffect(),
                    previousEffectDuration + duration, activeWeedIngredient.getEffectAmplifier()));
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
        CompoundTag tag = pStack.getTag();
        if (tag == null || !tag.contains("duration")) {
            return;
        }

        BaseWeedItem activeIngredient = getActiveWeedIngredient(pStack);
        if (activeIngredient == null) {
            return;
        }

        pTooltipComponents.add(WeedEffectHelper.getEffectTooltip(activeIngredient.getEffect(),
                tag.getInt("duration"), !activeIngredient.isVariableDuration()));
    }

    public float getEffectFactor() {
        return this.effectDurationMultiplier;
    }

    public @Nullable BaseWeedItem getActiveWeedIngredient(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null || !tag.contains("active_ingredient")) {
            return null;
        }

        String[] parts = tag.getString("active_ingredient").split("\\.");
        String activeIngredientName = parts[parts.length - 1];
        RegistryObject<Item> activeIngredient = RegistryObject.create(
                new ResourceLocation(SmokeleafIndustryMod.MOD_ID, activeIngredientName), ForgeRegistries.ITEMS);

        if (activeIngredient.get() instanceof BaseWeedItem activeIngredientItem) {
            return activeIngredientItem;
        }

        return null;
    }
}
