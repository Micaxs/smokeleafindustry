package net.micaxs.smokeleafindustry.datagen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, SmokeleafIndustryMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.WEEDS).add(
                ModItems.WHITE_WIDOW_WEED.get(),
                ModItems.SOUR_DIESEL_WEED.get(),
                ModItems.PURPLE_HAZE_WEED.get(),
                ModItems.LEMON_HAZE_WEED.get(),
                ModItems.BUBBLE_KUSH_WEED.get(),
                ModItems.BLUE_ICE_WEED.get(),
                ModItems.BUBBLEGUM_WEED.get()
        );

        this.tag(ModTags.WEED_BUDS).add(
                ModItems.WHITE_WIDOW_BUD.get(),
                ModItems.SOUR_DIESEL_BUD.get(),
                ModItems.PURPLE_HAZE_BUD.get(),
                ModItems.LEMON_HAZE_BUD.get(),
                ModItems.BUBBLE_KUSH_BUD.get(),
                ModItems.BLUE_ICE_BUD.get(),
                ModItems.BUBBLEGUM_BUD.get()
        );

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(
                        // Red Baja Hoodie
                        ModItems.HEMP_HELMET_RED.get(),
                        ModItems.HEMP_CHESTPLATE_RED.get(),
                        ModItems.HEMP_LEGGINGS_RED.get(),
                        ModItems.HEMP_BOOTS_RED.get(),

                        // Green Baja Hoodie
                        ModItems.HEMP_HELMET_GREEN.get(),
                        ModItems.HEMP_CHESTPLATE_GREEN.get(),
                        ModItems.HEMP_LEGGINGS_GREEN.get(),
                        ModItems.HEMP_BOOTS_GREEN.get(),

                        // Yellow Baja Hoodie
                        ModItems.HEMP_HELMET_YELLOW.get(),
                        ModItems.HEMP_CHESTPLATE_YELLOW.get(),
                        ModItems.HEMP_LEGGINGS_YELLOW.get(),
                        ModItems.HEMP_BOOTS_YELLOW.get()
                );

        this.tag(ModTags.WHITE_WIDOW_STRAIN).add(
                ModItems.WHITE_WIDOW_WEED.get(),
                ModItems.WHITE_WIDOW_EXTRACT.get()
        );
        this.tag(ModTags.SOUR_DIESEL_STRAIN).add(
                ModItems.SOUR_DIESEL_WEED.get(),
                ModItems.SOUR_DIESEL_EXTRACT.get()
        );
        this.tag(ModTags.PURPLE_HAZE_STRAIN).add(
                ModItems.PURPLE_HAZE_WEED.get(),
                ModItems.PURPLE_HAZE_EXTRACT.get()
        );
        this.tag(ModTags.LEMON_HAZE_STRAIN).add(
                ModItems.LEMON_HAZE_WEED.get(),
                ModItems.LEMON_HAZE_EXTRACT.get()
        );
        this.tag(ModTags.BUBBLE_KUSH_STRAIN).add(
                ModItems.BUBBLE_KUSH_WEED.get(),
                ModItems.BUBBLE_KUSH_EXTRACT.get()
        );
        this.tag(ModTags.BLUE_ICE_STRAIN).add(
                ModItems.BLUE_ICE_WEED.get(),
                ModItems.BLUE_ICE_EXTRACT.get()
        );
        this.tag(ModTags.BUBBLEGUM_STRAIN).add(
                ModItems.BUBBLEGUM_WEED.get(),
                ModItems.BUBBLEGUM_EXTRACT.get()
        );
    }
}
