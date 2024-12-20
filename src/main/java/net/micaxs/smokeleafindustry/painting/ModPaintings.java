package net.micaxs.smokeleafindustry.painting;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<PaintingVariant> WEED_LEAF = PAINTING_VARIANTS.register("weed_leaf",
            () -> new PaintingVariant(32, 32));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANTS.register(eventBus);
    }
}
