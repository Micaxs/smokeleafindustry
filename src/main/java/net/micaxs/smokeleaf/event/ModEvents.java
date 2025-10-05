package net.micaxs.smokeleaf.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.fluid.ModFluids;
import net.micaxs.smokeleaf.item.ModItems;
import net.micaxs.smokeleaf.villager.ModVillagers;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.STONER.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1 (pick 2) - Novice
            addRandomTrades(trades, 1, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.HEMP_FIBERS, 18), new ItemStack(Items.EMERALD, 1), 16, 2, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.HEMP_COAL, 5), new ItemStack(Items.EMERALD, 1), 16, 2, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.DRIED_TOBACCO_LEAF, 8), new ItemStack(Items.EMERALD, 1), 12, 2, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.HEMP_LEAF, 5), new ItemStack(Items.EMERALD, 1), 10, 2, 0.01f)
            );

            // Level 2 (pick 2) - Apprentice
            addRandomTrades(trades, 2, 1,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.WHITE_WIDOW_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BUBBLE_KUSH_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.LEMON_HAZE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.SOUR_DIESEL_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BLUE_ICE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BUBBLEGUM_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.PURPLE_HAZE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.OG_KUSH_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.JACK_HERER_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.GARY_PEYTON_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.AMNESIA_HAZE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.AK47_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.GHOST_TRAIN_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.GRAPE_APE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.COTTON_CANDY_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BANANA_KUSH_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.CARBON_FIBER_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BIRTHDAY_CAKE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BLUE_COOKIES_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.AFGHANI_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.MOONBOW_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.LAVA_CAKE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.JELLY_RANCHER_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.STRAWBERRY_SHORTCAKE_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.PINK_KUSH_BAG, 1), new ItemStack(Items.EMERALD, 1), 10, 5, 0.01f)
            );
            addRandomTrades(trades, 2, 1,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.TOBACCO, 10), new ItemStack(Items.EMERALD, 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BIO_COMPOSITE, 1), new ItemStack(Items.EMERALD, 3), 8, 5, 0.01f)
            );

            // Level 3 (pick 2) - Journeyman
            addRandomTrades(trades, 3, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModFluids.HASH_OIL_BUCKET, 1), new ItemStack(Items.EMERALD, 3), 4, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModFluids.HEMP_OIL_BUCKET, 1), new ItemStack(Items.EMERALD, 3), 4, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.EMPTY_TINCTURE, 4), new ItemStack(Items.EMERALD, 1), 4, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.INFUSED_BUTTER, 3), new ItemStack(Items.EMERALD, 1), 7, 10, 0.01f)
            );

            // Level 4 (pick 2) - Expert
            addRandomTrades(trades, 4, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.WEED_COOKIE, 1), new ItemStack(Items.EMERALD, 2), 8, 15, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.HASH_BROWNIE, 1), new ItemStack(Items.EMERALD, 3), 6, 15, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.DAB_RIG, 1), new ItemStack(Items.EMERALD, 3), 1, 15, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BONG, 1), new ItemStack(Items.EMERALD, 4), 1, 15, 0.01f)
            );

            // Level 5 (pick 2) - Master
            addRandomTrades(trades, 5, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.HERB_CAKE, 1), new ItemStack(Items.EMERALD, 6), 4, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.HASH_OIL_TINCTURE, 1), new ItemStack(Items.EMERALD, 5), 4, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.BLUNT, 2), new ItemStack(Items.EMERALD, 3), 6, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(ModItems.JOINT, 3), new ItemStack(Items.EMERALD, 2), 8, 20, 0.01f)
            );
        }

        if (event.getType() == ModVillagers.DEALER.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1 Trades
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.HEMP_SEEDS.get(), 1), 16, 2, 0.01f)
            );
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.TOBACCO_SEEDS.get(), 1), 16, 2, 0.01f)
            );

            // Level 2 Trades
            addRandomTrades(trades, 2, 1,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.WHITE_WIDOW_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.BUBBLE_KUSH_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.LEMON_HAZE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.SOUR_DIESEL_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.BLUE_ICE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BUBBLEGUM_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.PURPLE_HAZE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 3), new ItemStack(ModItems.OG_KUSH_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.JACK_HERER_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.GARY_PEYTON_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.AMNESIA_HAZE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.AK47_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.GHOST_TRAIN_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.GRAPE_APE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.COTTON_CANDY_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BANANA_KUSH_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.CARBON_FIBER_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.BIRTHDAY_CAKE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BLUE_COOKIES_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.AFGHANI_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.MOONBOW_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.LAVA_CAKE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.JELLY_RANCHER_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.STRAWBERRY_SHORTCAKE_WEED.get(), 1), 6, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.PINK_KUSH_WEED.get(), 1), 6, 5, 0.01f)
            );
            addRandomTrades(trades, 2, 1,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.WHITE_WIDOW_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.BUBBLE_KUSH_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.LEMON_HAZE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.SOUR_DIESEL_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.BLUE_ICE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BUBBLEGUM_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.PURPLE_HAZE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 3), new ItemStack(ModItems.OG_KUSH_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.JACK_HERER_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.GARY_PEYTON_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.AMNESIA_HAZE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.AK47_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.GHOST_TRAIN_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.GRAPE_APE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.COTTON_CANDY_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BANANA_KUSH_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.CARBON_FIBER_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.BIRTHDAY_CAKE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BLUE_COOKIES_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.AFGHANI_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.MOONBOW_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.LAVA_CAKE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.JELLY_RANCHER_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.STRAWBERRY_SHORTCAKE_GUMMY.get(), 1), 4, 5, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.PINK_KUSH_GUMMY.get(), 1), 4, 5, 0.01f)
            );

            addRandomTrades(trades, 3, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BASE_EXTRACT.get(), 1), 8, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 8), new ItemStack(ModFluids.HASH_OIL_BUCKET.get(), 1), 4, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 7), new ItemStack(ModFluids.HEMP_OIL_BUCKET.get(), 1), 4, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.BUTTER.get(), 1), 6, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 14), new ItemStack(ModItems.DNA_STRAND.get(), 1), 6, 10, 0.01f)
            );

            addRandomTrades(trades, 4, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 5), new ItemStack(ModItems.BIO_COMPOSITE.get(), 1), 10, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 10), new ItemStack(ModItems.DUAL_ARC_LAMP.get(), 1), 2, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 8), new ItemStack(ModItems.HEMP_PLASTIC.get(), 1), 6, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 7), new ItemStack(ModItems.UNFINISHED_HEMP_CORE.get(), 1), 2, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 4), new ItemStack(ModItems.INFUSED_BUTTER.get(), 1), 4, 10, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 14), new ItemStack(ModItems.CAT_URINE_BOTTLE.get(), 1), 2, 10, 0.01f)
            );

            addRandomTrades(trades, 5, 2,
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 10), new ItemStack(ModItems.DNA_STRAND.get(), 1), 4, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 14), new ItemStack(ModItems.HEMP_CORE.get(), 1), 2, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 8), new ItemStack(ModItems.HASH_OIL_TINCTURE.get(), 1), 2, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 13), new ItemStack(ModItems.MANUAL_GRINDER.get(), 1), 1, 20, 0.01f),
                    (pTrader, pRandom) -> new MerchantOffer(new ItemCost(Items.EMERALD, 16), new ItemStack(ModItems.HEMP_HAMMER.get(), 1), 1, 20, 0.01f)
            );
        }
    }


    private static void addRandomTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, int pick, VillagerTrades.ItemListing... candidates) {
        List<VillagerTrades.ItemListing> pool = new ArrayList<>(List.of(candidates));
        // Shuffle using default source (or use a cached java.util.Random if you prefer determinism per JVM run)
        Collections.shuffle(pool);
        List<VillagerTrades.ItemListing> levelList = trades.get(level);
        levelList.addAll(pool.subList(0, Math.min(pick, pool.size())));
    }

}
