package net.micaxs.smokeleaf;

import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.fluid.ModFluids;
import net.micaxs.smokeleaf.item.ModFoods;
import net.micaxs.smokeleaf.item.ModItems;
import net.micaxs.smokeleaf.item.custom.WeedDerivedItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SmokeleafIndustries.MODID);


    public static final Supplier<CreativeModeTab> SMOKELEAF_ITEMS_TAB = CREATIVE_MODE_TAB.register("smokeleaf_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HEMP_CORE.get()))
                    .title(Component.translatable("creativetab.smokeleafindustries.smokeleaf_items_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.UNFINISHED_HEMP_CORE);
                        output.accept(ModItems.HEMP_CORE);

                        output.accept(ModItems.HEMP_LEAF);
                        output.accept(ModItems.HEMP_FIBERS);
                        output.accept(ModItems.HEMP_STICK);
                        output.accept(ModItems.HEMP_FABRIC);
                        output.accept(ModItems.BIO_COMPOSITE);
                        output.accept(ModItems.HEMP_COAL);
                        output.accept(ModItems.HEMP_PLASTIC);

                        output.accept(ModBlocks.HEMP_STONE);
                        output.accept(ModBlocks.HEMP_STONE_SLAB);
                        output.accept(ModBlocks.HEMP_STONE_STAIRS);
                        output.accept(ModBlocks.HEMP_STONE_PRESSURE_PLATE);
                        output.accept(ModBlocks.HEMP_STONE_BUTTON);
                        output.accept(ModBlocks.HEMP_STONE_WALL);

                        output.accept(ModBlocks.HEMP_PLANKS);
                        output.accept(ModBlocks.HEMP_PLANK_SLAB);
                        output.accept(ModBlocks.HEMP_PLANK_STAIRS);
                        output.accept(ModBlocks.HEMP_PLANK_PRESSURE_PLATE);
                        output.accept(ModBlocks.HEMP_PLANK_BUTTON);
                        output.accept(ModBlocks.HEMP_PLANK_FENCE);
                        output.accept(ModBlocks.HEMP_PLANK_FENCE_GATE);
                        output.accept(ModBlocks.HEMP_PLANK_DOOR);
                        output.accept(ModBlocks.HEMP_PLANK_TRAPDOOR);

                        output.accept(ModBlocks.HEMP_BRICKS);
                        output.accept(ModBlocks.HEMP_BRICK_SLAB);
                        output.accept(ModBlocks.HEMP_BRICK_STAIRS);
                        output.accept(ModBlocks.HEMP_BRICK_WALL);

                        output.accept(ModBlocks.HEMP_CHISELED_STONE);
                        output.accept(ModBlocks.HEMP_CHISELED_STONE_SLAB);
                        output.accept(ModBlocks.HEMP_CHISELED_STONE_STAIRS);
                        output.accept(ModBlocks.HEMP_CHISELED_STONE_WALL);



                        output.accept(ModItems.TOBACCO);
                        output.accept(ModItems.TOBACCO_LEAF);
                        output.accept(ModItems.DRIED_TOBACCO_LEAF);

                        output.accept(ModBlocks.REFLECTOR);
                        output.accept(ModItems.HPS_LAMP);
                        output.accept(ModItems.DUAL_ARC_LAMP);
                        output.accept(ModBlocks.LED_LIGHT);
                        output.accept(ModBlocks.GROW_POT);

                        output.accept(ModItems.HEMP_HAMMER);
                        output.accept(ModItems.MAGNIFYING_GLASS);
                        output.accept(ModItems.MANUAL_GRINDER);
                        output.accept(ModItems.EMPTY_BAG);
                        output.accept(ModItems.JOINT);
                        output.accept(ModItems.BLUNT);
                        output.accept(ModItems.BONG);
                        output.accept(ModItems.DAB_RIG);
                        output.accept(ModItems.EMPTY_TINCTURE);
                        output.accept(ModItems.HASH_OIL_TINCTURE);
                        output.accept(ModItems.BASE_EXTRACT);
                        output.accept(ModItems.DNA_STRAND);

                        output.accept(ModItems.BUTTER);
                        output.accept(ModItems.INFUSED_BUTTER);
                        output.accept(ModItems.HERB_CAKE);
                        output.accept(ModItems.HASH_BROWNIE);
                        output.accept(ModItems.WEED_COOKIE);

                        output.accept(ModFluids.HEMP_OIL_BUCKET);
                        output.accept(ModFluids.HASH_OIL_BUCKET);
                        output.accept(ModFluids.HASH_OIL_SLUDGE_BUCKET);

                        output.accept(ModBlocks.GENERATOR);
                        output.accept(ModBlocks.GRINDER);
                        output.accept(ModBlocks.EXTRACTOR);
                        output.accept(ModBlocks.LIQUIFIER);
                        output.accept(ModBlocks.DRYER);
                        output.accept(ModBlocks.MUTATOR);
                        output.accept(ModBlocks.SYNTHESIZER);
                        output.accept(ModBlocks.SEQUENCER);
                        output.accept(ModBlocks.DRYING_RACK);


                        output.accept(ModItems.WORM_CASTINGS);
                        output.accept(ModItems.COMPOST);
                        output.accept(ModItems.MYCORRHIZAE);
                        output.accept(ModItems.DOLOMITE_LIME);
                        output.accept(ModItems.BLOOD_MEAL);
                        output.accept(ModItems.PHOSPHORUS_POWDER);
                        output.accept(ModItems.BAT_GUANO);
                        output.accept(ModItems.KELP_MEAL);
                        output.accept(ModItems.WOOD_ASH);
                        output.accept(ModItems.FISH_EMULSION);
                        output.accept(ModItems.BLOOM_BOOSTER);
                        output.accept(ModItems.FRUIT_FINISHER);
                        output.accept(ModItems.NITROGEN_BOOST);
                        output.accept(ModItems.POTASH_BOOST);
                        output.accept(ModItems.BALANCED_BOOST);
                        output.accept(ModItems.PHOSPHORUS_REDUCER);
                        output.accept(ModItems.POTASSIUM_REDUCER);


                    }).build());

    public static final Supplier<CreativeModeTab> SMOKELEAF_HERB_TAB = CREATIVE_MODE_TAB.register("smokeleaf_herb_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AMNESIA_HAZE_BUD.get()))
                    .title(Component.translatable("creativetab.smokeleafindustries.smokeleaf_herb_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.TOBACCO_SEEDS);
                        output.accept(ModItems.HEMP_SEEDS);
                        output.accept(ModItems.WHITE_WIDOW_SEEDS);
                        output.accept(ModItems.BUBBLE_KUSH_SEEDS);
                        output.accept(ModItems.LEMON_HAZE_SEEDS);
                        output.accept(ModItems.SOUR_DIESEL_SEEDS);
                        output.accept(ModItems.BLUE_ICE_SEEDS);
                        output.accept(ModItems.BUBBLEGUM_SEEDS);
                        output.accept(ModItems.PURPLE_HAZE_SEEDS);
                        output.accept(ModItems.OG_KUSH_SEEDS);
                        output.accept(ModItems.JACK_HERER_SEEDS);
                        output.accept(ModItems.GARY_PEYTON_SEEDS);
                        output.accept(ModItems.AMNESIA_HAZE_SEEDS);
                        output.accept(ModItems.AK47_SEEDS);
                        output.accept(ModItems.GHOST_TRAIN_SEEDS);
                        output.accept(ModItems.GRAPE_APE_SEEDS);
                        output.accept(ModItems.COTTON_CANDY_SEEDS);
                        output.accept(ModItems.BANANA_KUSH_SEEDS);
                        output.accept(ModItems.CARBON_FIBER_SEEDS);
                        output.accept(ModItems.BIRTHDAY_CAKE_SEEDS);
                        output.accept(ModItems.BLUE_COOKIES_SEEDS);
                        output.accept(ModItems.AFGHANI_SEEDS);
                        output.accept(ModItems.MOONBOW_SEEDS);
                        output.accept(ModItems.LAVA_CAKE_SEEDS);
                        output.accept(ModItems.JELLY_RANCHER_SEEDS);
                        output.accept(ModItems.STRAWBERRY_SHORTCAKE_SEEDS);
                        output.accept(ModItems.PINK_KUSH_SEEDS);

                        output.accept(ModItems.WHITE_WIDOW_BUD);
                        output.accept(ModItems.LEMON_HAZE_BUD);
                        output.accept(ModItems.SOUR_DIESEL_BUD);
                        output.accept(ModItems.BLUE_ICE_BUD);
                        output.accept(ModItems.BUBBLEGUM_BUD);
                        output.accept(ModItems.PURPLE_HAZE_BUD);
                        output.accept(ModItems.OG_KUSH_BUD);
                        output.accept(ModItems.JACK_HERER_BUD);
                        output.accept(ModItems.GARY_PEYTON_BUD);
                        output.accept(ModItems.AMNESIA_HAZE_BUD);
                        output.accept(ModItems.AK47_BUD);
                        output.accept(ModItems.GHOST_TRAIN_BUD);
                        output.accept(ModItems.GRAPE_APE_BUD);
                        output.accept(ModItems.COTTON_CANDY_BUD);
                        output.accept(ModItems.BANANA_KUSH_BUD);
                        output.accept(ModItems.CARBON_FIBER_BUD);
                        output.accept(ModItems.BIRTHDAY_CAKE_BUD);
                        output.accept(ModItems.BLUE_COOKIES_BUD);
                        output.accept(ModItems.AFGHANI_BUD);
                        output.accept(ModItems.MOONBOW_BUD);
                        output.accept(ModItems.LAVA_CAKE_BUD);
                        output.accept(ModItems.JELLY_RANCHER_BUD);
                        output.accept(ModItems.STRAWBERRY_SHORTCAKE_BUD);
                        output.accept(ModItems.PINK_KUSH_BUD);

                        output.accept(ModItems.WHITE_WIDOW_WEED);
                        output.accept(ModItems.LEMON_HAZE_WEED);
                        output.accept(ModItems.SOUR_DIESEL_WEED);
                        output.accept(ModItems.BLUE_ICE_WEED);
                        output.accept(ModItems.BUBBLEGUM_WEED);
                        output.accept(ModItems.PURPLE_HAZE_WEED);
                        output.accept(ModItems.OG_KUSH_WEED);
                        output.accept(ModItems.JACK_HERER_WEED);
                        output.accept(ModItems.GARY_PEYTON_WEED);
                        output.accept(ModItems.AMNESIA_HAZE_WEED);
                        output.accept(ModItems.AK47_WEED);
                        output.accept(ModItems.GHOST_TRAIN_WEED);
                        output.accept(ModItems.GRAPE_APE_WEED);
                        output.accept(ModItems.COTTON_CANDY_WEED);
                        output.accept(ModItems.BANANA_KUSH_WEED);
                        output.accept(ModItems.CARBON_FIBER_WEED);
                        output.accept(ModItems.BIRTHDAY_CAKE_WEED);
                        output.accept(ModItems.BLUE_COOKIES_WEED);
                        output.accept(ModItems.AFGHANI_WEED);
                        output.accept(ModItems.MOONBOW_WEED);
                        output.accept(ModItems.LAVA_CAKE_WEED);
                        output.accept(ModItems.JELLY_RANCHER_WEED);
                        output.accept(ModItems.STRAWBERRY_SHORTCAKE_WEED);
                        output.accept(ModItems.PINK_KUSH_WEED);

                        output.accept(ModItems.WHITE_WIDOW_EXTRACT);
                        output.accept(ModItems.LEMON_HAZE_EXTRACT);
                        output.accept(ModItems.SOUR_DIESEL_EXTRACT);
                        output.accept(ModItems.BLUE_ICE_EXTRACT);
                        output.accept(ModItems.BUBBLEGUM_EXTRACT);
                        output.accept(ModItems.PURPLE_HAZE_EXTRACT);
                        output.accept(ModItems.OG_KUSH_EXTRACT);
                        output.accept(ModItems.JACK_HERER_EXTRACT);
                        output.accept(ModItems.GARY_PEYTON_EXTRACT);
                        output.accept(ModItems.AMNESIA_HAZE_EXTRACT);
                        output.accept(ModItems.AK47_EXTRACT);
                        output.accept(ModItems.GHOST_TRAIN_EXTRACT);
                        output.accept(ModItems.GRAPE_APE_EXTRACT);
                        output.accept(ModItems.COTTON_CANDY_EXTRACT);
                        output.accept(ModItems.BANANA_KUSH_EXTRACT);
                        output.accept(ModItems.CARBON_FIBER_EXTRACT);
                        output.accept(ModItems.BIRTHDAY_CAKE_EXTRACT);
                        output.accept(ModItems.BLUE_COOKIES_EXTRACT);
                        output.accept(ModItems.AFGHANI_EXTRACT);
                        output.accept(ModItems.MOONBOW_EXTRACT);
                        output.accept(ModItems.LAVA_CAKE_EXTRACT);
                        output.accept(ModItems.JELLY_RANCHER_EXTRACT);
                        output.accept(ModItems.STRAWBERRY_SHORTCAKE_EXTRACT);
                        output.accept(ModItems.PINK_KUSH_EXTRACT);

                        output.accept(ModItems.WHITE_WIDOW_BAG);
                        output.accept(ModItems.LEMON_HAZE_BAG);
                        output.accept(ModItems.SOUR_DIESEL_BAG);
                        output.accept(ModItems.BLUE_ICE_BAG);
                        output.accept(ModItems.BUBBLEGUM_BAG);
                        output.accept(ModItems.PURPLE_HAZE_BAG);
                        output.accept(ModItems.OG_KUSH_BAG);
                        output.accept(ModItems.JACK_HERER_BAG);
                        output.accept(ModItems.GARY_PEYTON_BAG);
                        output.accept(ModItems.AMNESIA_HAZE_BAG);
                        output.accept(ModItems.AK47_BAG);
                        output.accept(ModItems.GHOST_TRAIN_BAG);
                        output.accept(ModItems.GRAPE_APE_BAG);
                        output.accept(ModItems.COTTON_CANDY_BAG);
                        output.accept(ModItems.BANANA_KUSH_BAG);
                        output.accept(ModItems.CARBON_FIBER_BAG);
                        output.accept(ModItems.BIRTHDAY_CAKE_BAG);
                        output.accept(ModItems.BLUE_COOKIES_BAG);
                        output.accept(ModItems.AFGHANI_BAG);
                        output.accept(ModItems.MOONBOW_BAG);
                        output.accept(ModItems.LAVA_CAKE_BAG);
                        output.accept(ModItems.JELLY_RANCHER_BAG);
                        output.accept(ModItems.STRAWBERRY_SHORTCAKE_BAG);
                        output.accept(ModItems.PINK_KUSH_BAG);

                        output.accept(ModItems.WHITE_WIDOW_GUMMY);
                        output.accept(ModItems.SOUR_DIESEL_GUMMY);
                        output.accept(ModItems.PURPLE_HAZE_GUMMY);
                        output.accept(ModItems.LEMON_HAZE_GUMMY);
                        output.accept(ModItems.BUBBLE_KUSH_GUMMY);
                        output.accept(ModItems.BLUE_ICE_GUMMY);
                        output.accept(ModItems.BUBBLEGUM_GUMMY);
                        output.accept(ModItems.OG_KUSH_GUMMY);
                        output.accept(ModItems.JACK_HERER_GUMMY);
                        output.accept(ModItems.GARY_PEYTON_GUMMY);
                        output.accept(ModItems.AMNESIA_HAZE_GUMMY);
                        output.accept(ModItems.AK47_GUMMY);
                        output.accept(ModItems.GHOST_TRAIN_GUMMY);
                        output.accept(ModItems.GRAPE_APE_GUMMY);
                        output.accept(ModItems.COTTON_CANDY_GUMMY);
                        output.accept(ModItems.BANANA_KUSH_GUMMY);
                        output.accept(ModItems.CARBON_FIBER_GUMMY);
                        output.accept(ModItems.BIRTHDAY_CAKE_GUMMY);
                        output.accept(ModItems.BLUE_COOKIES_GUMMY);
                        output.accept(ModItems.AFGHANI_GUMMY);
                        output.accept(ModItems.MOONBOW_GUMMY);
                        output.accept(ModItems.LAVA_CAKE_GUMMY);
                        output.accept(ModItems.JELLY_RANCHER_GUMMY);
                        output.accept(ModItems.STRAWBERRY_SHORTCAKE_GUMMY);
                        output.accept(ModItems.PINK_KUSH_GUMMY);

                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
