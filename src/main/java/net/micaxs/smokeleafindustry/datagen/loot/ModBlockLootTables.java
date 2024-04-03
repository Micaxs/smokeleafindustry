package net.micaxs.smokeleafindustry.datagen.loot;

import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.block.custom.*;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // Blocks drop them self
        this.dropSelf(ModBlocks.HERB_GRINDER_STATION.get());
        this.dropSelf(ModBlocks.HERB_EXTRACTOR.get());
        this.dropSelf(ModBlocks.HERB_GENERATOR.get());
        this.dropSelf(ModBlocks.HERB_MUTATION.get());

        this.dropSelf(ModBlocks.HEMP_STONE.get());
        this.dropSelf(ModBlocks.HEMP_PLANKS.get());

        this.dropSelf(ModBlocks.HASH_OIL_BLOCK.get());


        // White Widow Crop drops
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.WHITE_WIDOW_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(ModBlocks.WHITE_WIDOW_CROP.get(), createCropDrops(ModBlocks.WHITE_WIDOW_CROP.get(), ModItems.WHITE_WIDOW_BUD.get(), ModItems.WHITE_WIDOW_SEEDS.get(), lootitemcondition$builder));

        // Bubble Kush Crop drops
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.BUBBLE_KUSH_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(ModBlocks.BUBBLE_KUSH_CROP.get(), createCropDrops(ModBlocks.BUBBLE_KUSH_CROP.get(), ModItems.BUBBLE_KUSH_BUD.get(), ModItems.BUBBLE_KUSH_SEEDS.get(), lootitemcondition$builder2));

        // Lemon Haze Crop drops
        LootItemCondition.Builder lootitemcondition$builder3 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.LEMON_HAZE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(ModBlocks.LEMON_HAZE_CROP.get(), createCropDrops(ModBlocks.LEMON_HAZE_CROP.get(), ModItems.LEMON_HAZE_BUD.get(), ModItems.LEMON_HAZE_SEEDS.get(), lootitemcondition$builder3));

        // Sour Diesel Crop drops
        LootItemCondition.Builder lootitemcondition$builder4 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.SOUR_DIESEL_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(ModBlocks.SOUR_DIESEL_CROP.get(), createCropDrops(ModBlocks.SOUR_DIESEL_CROP.get(), ModItems.SOUR_DIESEL_BUD.get(), ModItems.SOUR_DIESEL_SEEDS.get(), lootitemcondition$builder4));


        // Blue Ice Crop drops
        LootItemCondition.Builder lootitemcondition$builder5 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.BLUE_ICE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(ModBlocks.BLUE_ICE_CROP.get(), createCropDrops(ModBlocks.BLUE_ICE_CROP.get(), ModItems.BLUE_ICE_BUD.get(), ModItems.BLUE_ICE_SEEDS.get(), lootitemcondition$builder5));
    }


    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
