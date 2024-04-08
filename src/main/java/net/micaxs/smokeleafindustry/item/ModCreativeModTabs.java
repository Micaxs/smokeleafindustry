package net.micaxs.smokeleafindustry.item;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SMOKELEAF_INDUSTRY_TAB = CREATIVE_MODE_TABS.register("smokeleaf_industry_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WHITE_WIDOW_BUD.get()))
                    .title(Component.translatable("creativetab.smokeleaf_industry_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        // Buds
                        pOutput.accept(ModItems.WHITE_WIDOW_BUD.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_BUD.get());
                        pOutput.accept(ModItems.LEMON_HAZE_BUD.get());
                        pOutput.accept(ModItems.SOUR_DIESEL_BUD.get());
                        pOutput.accept(ModItems.BLUE_ICE_BUD.get());
                        pOutput.accept(ModItems.BUBBLEGUM_BUD.get());

                        // Weed
                        pOutput.accept(ModItems.WHITE_WIDOW_WEED.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_WEED.get());
                        pOutput.accept(ModItems.LEMON_HAZE_WEED.get());
                        pOutput.accept(ModItems.SOUR_DIESEL_WEED.get());
                        pOutput.accept(ModItems.BLUE_ICE_WEED.get());
                        pOutput.accept(ModItems.BUBBLEGUM_WEED.get());

                        // Seeds
                        pOutput.accept(ModItems.WHITE_WIDOW_SEEDS.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_SEEDS.get());
                        pOutput.accept(ModItems.LEMON_HAZE_SEEDS.get());
                        pOutput.accept(ModItems.SOUR_DIESEL_SEEDS.get());
                        pOutput.accept(ModItems.BLUE_ICE_SEEDS.get());
                        pOutput.accept(ModItems.BUBBLEGUM_SEEDS.get());

                        // Extracts
                        pOutput.accept(ModItems.PURPLE_HAZE_EXTRACT.get());
                        pOutput.accept(ModItems.BLUE_ICE_EXTRACT.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_EXTRACT.get());
                        pOutput.accept(ModItems.BUBBLEGUM_EXTRACT.get());
                        pOutput.accept(ModItems.LEMON_HAZE_EXTRACT.get());
                        pOutput.accept(ModItems.SOUR_DIESEL_EXTRACT.get());
                        pOutput.accept(ModItems.WHITE_WIDOW_EXTRACT.get());

                        // Baggies
                        pOutput.accept(ModItems.EMPTY_BAG.get());
                        pOutput.accept(ModItems.WHITE_WIDOW_BAG.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_BAG.get());
                        pOutput.accept(ModItems.LEMON_HAZE_BAG.get());
                        pOutput.accept(ModItems.SOUR_DIESEL_BAG.get());
                        pOutput.accept(ModItems.BLUE_ICE_BAG.get());
                        pOutput.accept(ModItems.BUBBLEGUM_BAG.get());

                        // Other Items
                        pOutput.accept(ModItems.HASH_OIL_BUCKET.get());
                        pOutput.accept(ModItems.BONG.get());
                        pOutput.accept(ModItems.BLUNT.get());
                        pOutput.accept(ModItems.GRINDER.get());
                        pOutput.accept(ModItems.HERB_CAKE.get());

                        pOutput.accept(ModBlocks.HEMP_STONE.get());
                        pOutput.accept(ModBlocks.HEMP_PLANKS.get());
                        pOutput.accept(ModItems.HEMP_STICK.get());
                        pOutput.accept(ModItems.HEMP_CORE.get());

                        pOutput.accept(ModBlocks.HEMP_MACHINE_BLOCK.get());
                        pOutput.accept(ModBlocks.HERB_GENERATOR.get());
                        pOutput.accept(ModBlocks.HERB_GRINDER_STATION.get());
                        pOutput.accept(ModBlocks.HERB_EXTRACTOR.get());
                        pOutput.accept(ModBlocks.HERB_MUTATION.get());
                        pOutput.accept(ModBlocks.HERB_EVAPORATOR.get());

                        pOutput.accept(ModBlocks.GROW_LIGHT.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
