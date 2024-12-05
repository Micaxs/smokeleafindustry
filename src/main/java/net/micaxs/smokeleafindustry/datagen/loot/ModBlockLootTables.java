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
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

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
        this.dropSelf(ModBlocks.HERB_EVAPORATOR.get());
        this.dropSelf(ModBlocks.HEMP_MACHINE_BLOCK.get());
        this.dropSelf(ModBlocks.HEMP_SPINNER.get());
        this.dropSelf(ModBlocks.HEMP_WEAVER.get());
        this.dropSelf(ModBlocks.GROW_LIGHT.get());
        this.dropSelf(ModBlocks.HEMP_STONE.get());
        this.dropSelf(ModBlocks.HEMP_PLANKS.get());
        this.dropSelf(ModBlocks.HASH_OIL_BLOCK.get());
        this.dropSelf(ModBlocks.HASH_OIL_SLUDGE_BLOCK.get());
        this.dropSelf(ModBlocks.HEMP_WOOL.get());

        // Weed crops
        addCropLoot(ModBlocks.WHITE_WIDOW_CROP, ModItems.WHITE_WIDOW_BUD, ModItems.WHITE_WIDOW_SEEDS, ModItems.HEMP_LEAF);
        addCropLoot(ModBlocks.BUBBLE_KUSH_CROP, ModItems.BUBBLE_KUSH_BUD, ModItems.BUBBLE_KUSH_SEEDS, ModItems.HEMP_LEAF);
        addCropLoot(ModBlocks.LEMON_HAZE_CROP, ModItems.LEMON_HAZE_BUD, ModItems.LEMON_HAZE_SEEDS, ModItems.HEMP_LEAF);
        addCropLoot(ModBlocks.SOUR_DIESEL_CROP, ModItems.SOUR_DIESEL_BUD, ModItems.SOUR_DIESEL_SEEDS, ModItems.HEMP_LEAF);
        addCropLoot(ModBlocks.BLUE_ICE_CROP, ModItems.BLUE_ICE_BUD, ModItems.BLUE_ICE_SEEDS, ModItems.HEMP_LEAF);
        addCropLoot(ModBlocks.BUBBLEGUM_CROP, ModItems.BUBBLEGUM_BUD, ModItems.BUBBLEGUM_SEEDS, ModItems.HEMP_LEAF);
        addCropLoot(ModBlocks.PURPLE_HAZE_CROP, ModItems.PURPLE_HAZE_BUD, ModItems.PURPLE_HAZE_SEEDS, ModItems.HEMP_LEAF);
    }

    private void addCropLoot(RegistryObject<Block> cropBlock, Supplier<Item> budSupplier, Supplier<Item> seedsSupplier, Supplier<Item> leafSupplier) {
        LootItemCondition.Builder cropIsHarvestable = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(cropBlock.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseWeedCropBlock.AGE, 10)
                );
        LootItemCondition.Builder cropIsBottomSegment = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(cropBlock.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseWeedCropBlock.TOP, false)
                );

        this.add(cropBlock.get(), createCropDropFull(budSupplier.get(), seedsSupplier.get(), leafSupplier.get(), cropIsHarvestable, cropIsBottomSegment));
    }

    private LootTable.Builder createCropDropFull(Item pBudItem, Item pSeedsItem, Item pLeafItem, LootItemCondition.Builder cropIsHarvestable, LootItemCondition.Builder cropIsBottomSegment) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()  // 1 Seed drop always
                        .when(cropIsBottomSegment)
                        .add(LootItem.lootTableItem(pSeedsItem))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                )
                .withPool(LootPool.lootPool()  // Bud drop with condition
                        .when(cropIsHarvestable.and(cropIsBottomSegment))
                        .add(LootItem.lootTableItem(pBudItem)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.55F, 1)))
                )
                .withPool(LootPool.lootPool()  // Leaf drop with condition
                        .when(cropIsHarvestable.and(cropIsBottomSegment))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .add(LootItem.lootTableItem(pLeafItem)));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
