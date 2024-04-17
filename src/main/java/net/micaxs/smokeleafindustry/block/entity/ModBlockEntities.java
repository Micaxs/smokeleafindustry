package net.micaxs.smokeleafindustry.block.entity;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<HerbGrinderStationBlockEntity>> HERB_GRINDER_BE =
            BLOCK_ENTITIES.register("herb_grinder_be", () ->
                    BlockEntityType.Builder.of(HerbGrinderStationBlockEntity::new,
                            ModBlocks.HERB_GRINDER_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<HerbExtractorBlockEntity>> HERB_EXTRACTOR_BE =
            BLOCK_ENTITIES.register("herb_extractor_be", () ->
                    BlockEntityType.Builder.of(HerbExtractorBlockEntity::new,
                            ModBlocks.HERB_EXTRACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<HerbGeneratorBlockEntity>> HERB_GENERATOR_BE =
            BLOCK_ENTITIES.register("herb_generator_be", () ->
                    BlockEntityType.Builder.of(HerbGeneratorBlockEntity::new,
                            ModBlocks.HERB_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<HerbMutationBlockEntity>> HERB_MUTATION_BE =
            BLOCK_ENTITIES.register("herb_mutation_be", () ->
                    BlockEntityType.Builder.of(HerbMutationBlockEntity::new,
                            ModBlocks.HERB_MUTATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<HerbEvaporatorBlockEntity>> HERB_EVAPORATOR_BE =
            BLOCK_ENTITIES.register("herb_evaporator_be", () ->
                    BlockEntityType.Builder.of(HerbEvaporatorBlockEntity::new,
                            ModBlocks.HERB_EVAPORATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<HempSpinnerBlockEntity>> HEMP_SPINNER_BE =
            BLOCK_ENTITIES.register("hemp_spinner_be", () ->
                    BlockEntityType.Builder.of(HempSpinnerBlockEntity::new,
                            ModBlocks.HEMP_SPINNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<HempWeaverBlockEntity>> HEMP_WEAVER_BE =
            BLOCK_ENTITIES.register("hemp_weaver_be", () ->
                    BlockEntityType.Builder.of(HempWeaverBlockEntity::new,
                            ModBlocks.HEMP_WEAVER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
