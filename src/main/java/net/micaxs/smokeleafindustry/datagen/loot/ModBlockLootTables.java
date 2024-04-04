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

        this.dropSelf(ModBlocks.HEMP_STONE.get());
        this.dropSelf(ModBlocks.HEMP_PLANKS.get());

        this.dropSelf(ModBlocks.HASH_OIL_BLOCK.get());

        addCropLoot(ModBlocks.WHITE_WIDOW_CROP, ModItems::getWhiteWidowBud, ModItems::getWhiteWidowSeeds);
        addCropLoot(ModBlocks.BUBBLE_KUSH_CROP, ModItems::getBubbleKushBud, ModItems::getBubbleKushSeeds);
        addCropLoot(ModBlocks.LEMON_HAZE_CROP, ModItems::getLemonHazeBud, ModItems::getLemonHazeSeeds);
        addCropLoot(ModBlocks.SOUR_DIESEL_CROP, ModItems::getSourDieselBud, ModItems::getSourDieselSeeds);
        addCropLoot(ModBlocks.BLUE_ICE_CROP, ModItems::getBlueIceBud, ModItems::getBlueIceSeeds);
        addCropLoot(ModBlocks.BUBBLEGUM_CROP, ModItems::getBubblegumBud, ModItems::getBubblegumSeeds);
    }

    private void addCropLoot(RegistryObject<Block> cropBlock, Supplier<Item> budSupplier, Supplier<Item> seedsSupplier) {
        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(cropBlock.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseWeedCropBlock.AGE, 10));
        this.add(cropBlock.get(), createCropDrops(cropBlock.get(), budSupplier.get(), seedsSupplier.get(), lootItemConditionBuilder));
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
