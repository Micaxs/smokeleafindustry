package net.micaxs.smokeleafindustry.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.villager.ModVillagers;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = SmokeleafIndustryMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {

        if (event.getType() == ModVillagers.HERB_DEALER.get()) {

            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1 Trades
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.WHITE_WIDOW_SEEDS.get(), 1),
                    5, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.WHITE_WIDOW_WEED.get(), 2),
                    5, 6, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(ModItems.LEMON_HAZE_WEED.get(), 2),
                    5, 6, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.WHITE_WIDOW_SEEDS.get(), 3),
                    new ItemStack(Items.EMERALD, 1),
                    10, 4, 0.02f));


            // Level 2 Trades
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(ModItems.BUBBLE_KUSH_SEEDS.get(), 1),
                    6, 6, 0.035f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(ModItems.BUBBLE_KUSH_WEED.get(), 1),
                    8, 8, 0.035f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BUBBLE_KUSH_SEEDS.get(), 2),
                    new ItemStack(Items.EMERALD, 1),
                    10, 12, 0.035f));


            // Level 3 Trades
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(ModItems.LEMON_HAZE_SEEDS.get(), 1),
                    8, 10, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModItems.BUBBLEGUM_WEED.get(), 1),
                    8, 8, 0.035f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ModItems.SOUR_DIESEL_WEED.get(), 1),
                    8, 11, 0.035f));


            // Level 4 Trades
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 12),
                    new ItemStack(ModItems.SOUR_DIESEL_SEEDS.get(), 1),
                    10, 16, 0.035f));

            // Level 5 Trades
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.BLUE_ICE_SEEDS.get(), 1),
                    6, 18, 0.035f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.BUBBLEGUM_SEEDS.get(), 1),
                    6, 20, 0.035f));

        }

    }

}
