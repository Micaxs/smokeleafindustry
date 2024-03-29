package net.micaxs.smokeleafindustry.item;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.fluid.ModFluids;
import net.micaxs.smokeleafindustry.item.custom.BluntItem;
import net.micaxs.smokeleafindustry.item.custom.BongItem;
import net.micaxs.smokeleafindustry.item.custom.weeds.BubbleKushWeedItem;
import net.micaxs.smokeleafindustry.item.custom.weeds.WhiteWidowWeedItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<Item> WHITE_WIDOW_BUD = ITEMS.register("white_widow_bud",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_WIDOW_SEEDS = ITEMS.register("white_widow_seeds",
            () -> new ItemNameBlockItem(ModBlocks.WHITE_WIDOW_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> WHITE_WIDOW_WEED = ITEMS.register("white_widow_weed",
            () -> new WhiteWidowWeedItem(new Item.Properties()));

    public static final RegistryObject<Item> BUBBLE_KUSH_SEEDS = ITEMS.register("bubble_kush_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BUBBLE_KUSH_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> BUBBLE_KUSH_BUD = ITEMS.register("bubble_kush_bud",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BUBBLE_KUSH_WEED = ITEMS.register("bubble_kush_weed",
            () -> new BubbleKushWeedItem(new Item.Properties()));



    /* Extracts */
    public static final RegistryObject<Item> BLUE_ICE_EXTRACT = ITEMS.register("blueice_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> BUBBLEGUM_EXTRACT = ITEMS.register("bubblegum_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> BUBBLE_KUSH_EXTRACT = ITEMS.register("bubblekush_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> LEMON_HAZE_EXTRACT = ITEMS.register("lemonhaze_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PURPLE_HAZE_EXTRACT = ITEMS.register("purplehaze_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> SOUR_DIESEL_EXTRACT = ITEMS.register("sourdiesel_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> WHITE_WIDOW_EXTRACT = ITEMS.register("whitewidow_extract",
            () -> new Item(new Item.Properties().stacksTo(64)));



    public static final RegistryObject<Item> HASH_OIL_BUCKET = ITEMS.register("hash_oil_bucket",
            () -> new BucketItem(ModFluids.SOURCE_HASH_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


    public static final RegistryObject<Item> BONG = ITEMS.register("bong",
            () -> new BongItem(new Item.Properties().stacksTo(1).fireResistant()));

    public static final RegistryObject<Item> BLUNT = ITEMS.register("blunt",
            () -> new BluntItem(new Item.Properties().stacksTo(64)));


    public static final RegistryObject<Item> HASH_BROWNIE = ITEMS.register("hash_brownie",
            () -> new Item(new Item.Properties().food(ModFoods.HASH_BROWNIE)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
