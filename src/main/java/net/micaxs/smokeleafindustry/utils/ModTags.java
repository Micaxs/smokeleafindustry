package net.micaxs.smokeleafindustry.utils;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> WEEDS = tag("weeds");
    public static final TagKey<Item> WEED_BUDS = tag("weed_buds");
    public static final TagKey<Item> WHITE_WIDOW_STRAIN = tag("white_widow_strain");
    public static final TagKey<Item> SOUR_DIESEL_STRAIN = tag("sour_diesel_strain");
    public static final TagKey<Item> LEMON_HAZE_STRAIN = tag("lemon_haze_strain");
    public static final TagKey<Item> BUBBLE_KUSH_STRAIN = tag("bubble_kush_strain");
    public static final TagKey<Item> BUBBLEGUM_STRAIN = tag("bubblegum_strain");
    public static final TagKey<Item> BLUE_ICE_STRAIN = tag("blue_ice_strain");
    public static final TagKey<Item> PURPLE_HAZE_STRAIN = tag("purple_haze_strain");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(new ResourceLocation(SmokeleafIndustryMod.MOD_ID, name));
    }
}
