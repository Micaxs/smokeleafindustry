package net.micaxs.smokeleafindustry.datagen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.block.custom.BubbleKushCropBlock;
import net.micaxs.smokeleafindustry.block.custom.WhiteWidowCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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

        //makeWhiteWidowCrop((CropBlock) ModBlocks.WHITE_WIDOW_CROP.get(), "white_widow_stage_", "white_widow_stage_");
        //makeBubbleKushCrop(((CropBlock) ModBlocks.BUBBLE_KUSH_CROP.get()), "bubble_kush_stage_", "bubble_kush_stage_");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
