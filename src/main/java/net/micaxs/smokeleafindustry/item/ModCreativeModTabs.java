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

                        // Weed
                        pOutput.accept(ModItems.WHITE_WIDOW_WEED.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_WEED.get());

                        // Seeds
                        pOutput.accept(ModItems.WHITE_WIDOW_SEEDS.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_SEEDS.get());

                        // Extracts
                        pOutput.accept(ModItems.PURPLE_HAZE_EXTRACT.get());
                        pOutput.accept(ModItems.BLUE_ICE_EXTRACT.get());
                        pOutput.accept(ModItems.BUBBLE_KUSH_EXTRACT.get());
                        pOutput.accept(ModItems.BUBBLEGUM_EXTRACT.get());
                        pOutput.accept(ModItems.LEMON_HAZE_EXTRACT.get());
                        pOutput.accept(ModItems.SOUR_DIESEL_EXTRACT.get());
                        pOutput.accept(ModItems.WHITE_WIDOW_EXTRACT.get());

                        // Other Items
                        pOutput.accept(ModItems.HASH_OIL_BUCKET.get());
                        pOutput.accept(ModItems.BONG.get());
                        pOutput.accept(ModItems.BLUNT.get());
                        pOutput.accept(ModItems.HASH_BROWNIE.get());

                        pOutput.accept(ModBlocks.HERB_GRINDER_STATION.get());
                        pOutput.accept(ModBlocks.HERB_EXTRACTOR.get());
                        pOutput.accept(ModBlocks.HERB_GENERATOR.get());
                        pOutput.accept(ModBlocks.HERB_MUTATION.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
