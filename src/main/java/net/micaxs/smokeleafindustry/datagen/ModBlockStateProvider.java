package net.micaxs.smokeleafindustry.datagen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.block.custom.BaseWeedCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SmokeleafIndustryMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(ModBlocks.HERB_GRINDER_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/herb_grinder_station")));

        horizontalBlock(ModBlocks.HERB_EXTRACTOR.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/herb_extractor")));

        horizontalBlock(ModBlocks.HERB_GENERATOR.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/herb_generator")));

        horizontalBlock(ModBlocks.HERB_MUTATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/herb_mutation")));

        horizontalBlock(ModBlocks.HERB_EVAPORATOR.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/herb_evaporator")));

        horizontalBlock(ModBlocks.HEMP_SPINNER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/hemp_spinner")));

        horizontalBlock(ModBlocks.HEMP_WEAVER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/hemp_weaver")));

        horizontalBlock(ModBlocks.GROW_LIGHT.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/grow_light")));

        blockWithItem(ModBlocks.HEMP_MACHINE_BLOCK);
        blockWithItem(ModBlocks.HEMP_STONE);
        blockWithItem(ModBlocks.HEMP_PLANKS);
        blockWithItem(ModBlocks.HEMP_WOOL);

        makeWeedCrop((BaseWeedCropBlock) ModBlocks.WHITE_WIDOW_CROP.get(), "white_widow_stage_", "whitewidow/white_widow_stage_");
        makeWeedCrop((BaseWeedCropBlock) ModBlocks.BUBBLE_KUSH_CROP.get(), "bubble_kush_stage_", "bubblekush/bubble_kush_stage_");
        makeWeedCrop((BaseWeedCropBlock) ModBlocks.LEMON_HAZE_CROP.get(), "lemon_haze_stage_", "lemonhaze/lemon_haze_stage_");
        makeWeedCrop((BaseWeedCropBlock) ModBlocks.SOUR_DIESEL_CROP.get(), "sour_diesel_stage_", "sourdiesel/sour_diesel_stage_");
        makeWeedCrop((BaseWeedCropBlock) ModBlocks.BLUE_ICE_CROP.get(), "blue_ice_stage_", "blueice/blue_ice_stage_");
        makeWeedCrop((BaseWeedCropBlock) ModBlocks.BUBBLEGUM_CROP.get(), "bubblegum_stage_", "bubblegum/bubblegum_stage_");
        makeWeedCrop((BaseWeedCropBlock) ModBlocks.PURPLE_HAZE_CROP.get(), "purple_haze_stage_", "purplehaze/purple_haze_stage_");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void makeWeedCrop(BaseWeedCropBlock cropBlock, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = (blockState) -> {
            if (blockState.getValue(cropBlock.getAgeProperty()) >= cropBlock.getTallAge() && !blockState.getValue(cropBlock.getTop())) {
                blockState = blockState.setValue(cropBlock.getAgeProperty(), cropBlock.getTallAge() - 1);
            }
            return weedCropModel(blockState, cropBlock, modelName, textureName);
        };
        getVariantBuilder(cropBlock).forAllStatesExcept(function);
    }

    private ConfiguredModel[] weedCropModel(BlockState blockState, BaseWeedCropBlock cropBlock, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(
                modelName + blockState.getValue(cropBlock.getAgeProperty()),
                new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "block/" + textureName + blockState.getValue(cropBlock.getAgeProperty()))
                ).renderType("cutout"));
        return models;
    }
}
