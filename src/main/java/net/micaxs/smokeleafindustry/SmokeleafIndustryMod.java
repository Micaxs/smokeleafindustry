package net.micaxs.smokeleafindustry;

import com.mojang.logging.LogUtils;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.block.entity.ModBlockEntities;
import net.micaxs.smokeleafindustry.effect.ModEffects;
import net.micaxs.smokeleafindustry.fluid.ModFluidTypes;
import net.micaxs.smokeleafindustry.fluid.ModFluids;
import net.micaxs.smokeleafindustry.item.ModCreativeModTabs;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.loot.ModLootModifiers;
import net.micaxs.smokeleafindustry.network.PacketHandler;
import net.micaxs.smokeleafindustry.painting.ModPaintings;
import net.micaxs.smokeleafindustry.recipe.ModRecipes;
import net.micaxs.smokeleafindustry.screen.*;
import net.micaxs.smokeleafindustry.sound.ModSounds;
import net.micaxs.smokeleafindustry.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SmokeleafIndustryMod.MOD_ID)
public class SmokeleafIndustryMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "smokeleafindustry";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public SmokeleafIndustryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModSounds.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModPaintings.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        PacketHandler.register();
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.HERB_GRINDER_MENU.get(), HerbGrinderStationScreen::new);
            MenuScreens.register(ModMenuTypes.HERB_EXTRACTOR_MENU.get(), HerbExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.HERB_GENERATOR_MENU.get(), HerbGeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.HERB_MUTATION_MENU.get(), HerbMutationScreen::new);
            MenuScreens.register(ModMenuTypes.HERB_EVAPORATOR_MENU.get(), HerbEvaporatorScreen::new);
            MenuScreens.register(ModMenuTypes.HEMP_SPINNER_MENU.get(), HempSpinnerScreen::new);
            MenuScreens.register(ModMenuTypes.HEMP_WEAVER_MENU.get(), HempWeaverScreen::new);

            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HASH_OIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HASH_OIL.get(), RenderType.translucent());
        }
    }

}
