package net.micaxs.smokeleafindustry.villager;

import com.google.common.collect.ImmutableSet;
import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, SmokeleafIndustryMod.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, SmokeleafIndustryMod.MOD_ID);


    // TODO: Change the HEMP_PLANKS to an actual workstation for this dude...
    public static final RegistryObject<PoiType> HERB_POI = POI_TYPES.register("herb_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.HEMP_PLANKS.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> HERB_DEALER = VILLAGER_PROFESSIONS.register("herb_dealer",
            () -> new VillagerProfession("herb_dealer",
                    holder -> holder.get() == HERB_POI.get(),
                    holder -> holder.get() == HERB_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_NO));

    public static void register(IEventBus modEventBus) {
        POI_TYPES.register(modEventBus);
        VILLAGER_PROFESSIONS.register(modEventBus);
    }
}
