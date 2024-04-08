package net.micaxs.smokeleafindustry.datagen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, SmokeleafIndustryMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("white_widow_seeds_from_grass", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(net.minecraft.world.level.block.Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.10f).build()
        }, ModItems.WHITE_WIDOW_SEEDS.get()));

        add("bubble_kush_seeds_from_grass", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(net.minecraft.world.level.block.Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.01f).build()
        }, ModItems.BUBBLE_KUSH_SEEDS.get()));
    }
}
