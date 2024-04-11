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
                    new ItemStack(Items.IRON_INGOT, 2),
                    new ItemStack(ModItems.WHITE_WIDOW_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.IRON_INGOT, 3),
                    new ItemStack(ModItems.BUBBLE_KUSH_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 2),
                    new ItemStack(ModItems.LEMON_HAZE_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 3),
                    new ItemStack(ModItems.SOUR_DIESEL_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 2),
                    new ItemStack(ModItems.BLUE_ICE_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 3),
                    new ItemStack(ModItems.BUBBLEGUM_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.NETHERITE_INGOT, 1),
                    new ItemStack(ModItems.PURPLE_HAZE_BAG.get(), 1),
                    64, 3, 0.02f));

            // Level 2 Trades
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 3),
                    new ItemStack(ModItems.SOUR_DIESEL_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 16),
                    new ItemStack(ModItems.BLUE_ICE_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 20),
                    new ItemStack(ModItems.BUBBLEGUM_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.NETHERITE_INGOT, 1),
                    new ItemStack(ModItems.PURPLE_HAZE_BAG.get(), 1),
                    64, 3, 0.02f));

            // Level 3 Trades
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 16),
                    new ItemStack(ModItems.BLUE_ICE_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 20),
                    new ItemStack(ModItems.BUBBLEGUM_BAG.get(), 1),
                    64, 3, 0.02f));

            // Level 4 Trades
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 16),
                    new ItemStack(ModItems.BLUE_ICE_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 20),
                    new ItemStack(ModItems.BUBBLEGUM_BAG.get(), 1),
                    64, 3, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.NETHERITE_INGOT, 1),
                    new ItemStack(ModItems.PURPLE_HAZE_BAG.get(), 1),
                    64, 3, 0.02f));

        }

    }

}

//mcmeta
//animation {
//    interpolate
//
//}