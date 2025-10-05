package net.micaxs.smokeleaf.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties HERB_CAKE = new FoodProperties.Builder()
            .alwaysEdible()
            .nutrition(6)
            .saturationModifier(0.4f)
            .build();

    public static final FoodProperties HASH_BROWNIE = new FoodProperties.Builder()
            .alwaysEdible()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build();

    public static final FoodProperties WEED_COOKIE = new FoodProperties.Builder()
            .alwaysEdible()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build();

    public static final FoodProperties WEED_GUMMY = new FoodProperties.Builder()
            .alwaysEdible()
            .nutrition(4)
            .saturationModifier(0.4f)
            .build();

}
