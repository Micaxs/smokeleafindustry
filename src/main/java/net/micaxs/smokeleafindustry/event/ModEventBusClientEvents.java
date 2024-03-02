package net.micaxs.smokeleafindustry.event;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.entity.ModBlockEntities;
import net.micaxs.smokeleafindustry.block.entity.renderer.HerbExtractorBlockEntityRenderer;
import net.micaxs.smokeleafindustry.block.entity.renderer.HerbGrinderBlockEntityRenderer;
import net.micaxs.smokeleafindustry.block.entity.renderer.HerbMutationBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SmokeleafIndustryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.HERB_GRINDER_BE.get(), HerbGrinderBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.HERB_EXTRACTOR_BE.get(), HerbExtractorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.HERB_MUTATION_BE.get(), HerbMutationBlockEntityRenderer::new);
    }
}
