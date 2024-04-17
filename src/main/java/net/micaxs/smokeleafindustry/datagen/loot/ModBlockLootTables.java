package net.micaxs.smokeleafindustry.datagen.loot;

import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.block.custom.*;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
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
        this.dropSelf(ModBlocks.HEMP_WOOL.get());


        addCropLoot(ModBlocks.WHITE_WIDOW_CROP, ModItems::getWhiteWidowBud, ModItems::getWhiteWidowSeeds, ModItems::getHempLeaf);
        addCropLoot(ModBlocks.BUBBLE_KUSH_CROP, ModItems::getBubbleKushBud, ModItems::getBubbleKushSeeds, ModItems::getHempLeaf);
        addCropLoot(ModBlocks.LEMON_HAZE_CROP, ModItems::getLemonHazeBud, ModItems::getLemonHazeSeeds, ModItems::getHempLeaf);
        addCropLoot(ModBlocks.SOUR_DIESEL_CROP, ModItems::getSourDieselBud, ModItems::getSourDieselSeeds, ModItems::getHempLeaf);
        addCropLoot(ModBlocks.BLUE_ICE_CROP, ModItems::getBlueIceBud, ModItems::getBlueIceSeeds, ModItems::getHempLeaf);
        addCropLoot(ModBlocks.BUBBLEGUM_CROP, ModItems::getBubblegumBud, ModItems::getBubblegumSeeds, ModItems::getHempLeaf);
        addCropLoot(ModBlocks.PURPLE_HAZE_CROP, ModItems::getPurpleHazeBud, ModItems::getPurpleHazeSeeds, ModItems::getHempLeaf);
    }

    private void addCropLoot(RegistryObject<Block> cropBlock, Supplier<Item> budSupplier, Supplier<Item> seedsSupplier, Supplier<Item> leafSupplier) {
        LootItemCondition.Builder lootItemConditionBuilder10 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(cropBlock.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(cropBlock.get(), createCropDropFull(cropBlock.get(), budSupplier.get(), seedsSupplier.get(), leafSupplier.get(), lootItemConditionBuilder10));
    }

    protected LootTable.Builder createCropDropFull(Block pCropBlock, Item pBudItem, Item pSeedsItem, Item pLeafItem, LootItemCondition.Builder pDropGrownCropCondition) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()  // Seed drop with condition
                        .when(pDropGrownCropCondition)
                        .add(LootItem.lootTableItem(pSeedsItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .withPool(LootPool.lootPool()  // Bud drop with condition
                        .when(pDropGrownCropCondition)
                        .add(LootItem.lootTableItem(pBudItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.55F, 1))))
                .withPool(LootPool.lootPool()  // Leaf drop with condition
                        .when(pDropGrownCropCondition)
                        .add(LootItem.lootTableItem(pLeafItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
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