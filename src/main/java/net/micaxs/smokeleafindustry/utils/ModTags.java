package net.micaxs.smokeleafindustry.utils;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> WEEDS = tag("weeds");
    public static final TagKey<Item> WEED_BUDS = tag("weed_buds");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(new ResourceLocation(SmokeleafIndustryMod.MOD_ID, name));
    }
}
