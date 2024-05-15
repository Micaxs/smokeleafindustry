package net.micaxs.smokeleafindustry.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties HERB_CAKE = new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(6)
            .saturationMod(0.4f)
            .build();
    public static final FoodProperties HASH_BROWNIE = new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(2)
            .saturationMod(0.1f)
            .fast()
            .build();
}
