package net.micaxs.smokeleafindustry.datagen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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

        horizontalBlock(ModBlocks.GROW_LIGHT.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/grow_light")));

        //makeWhiteWidowCrop((CropBlock) ModBlocks.WHITE_WIDOW_CROP.get(), "white_widow_stage_", "white_widow_stage_");
        //makeBubbleKushCrop(((CropBlock) ModBlocks.BUBBLE_KUSH_CROP.get()), "bubble_kush_stage_", "bubble_kush_stage_");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
