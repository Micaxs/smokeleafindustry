package net.micaxs.smokeleafindustry.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties HERB_CAKE = new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(6)
            .saturationMod(0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200), 0.4f).build();

}
