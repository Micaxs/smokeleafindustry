package net.micaxs.smokeleaf.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.micaxs.smokeleaf.effect.ModEffects;

import net.minecraft.util.RandomSource;

/**
 * Global Loot Modifier for BakedLuckEffect.
 * Only affects ore blocks and applies a +5 Fortune bonus on top of existing Fortune.
 */
//public final class BakedLuckModifier extends LootModifier {
//
//    public static final MapCodec<BakedLuckModifier> CODEC = RecordCodecBuilder.mapCodec(
//            inst -> LootModifier.codecStart(inst).apply(inst, BakedLuckModifier::new)
//    );
//
//    protected BakedLuckModifier(LootItemCondition[] conditions) {
//        super(conditions);
//    }
//
//    @Override
//    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
//        Level level = context.getLevel();
//        if (level.isClientSide()) return generatedLoot;
//
//        // Check for player with Baked Luck effect
//        Player player = null;
//        if (context.hasParam(LootContextParams.THIS_ENTITY) && context.getParam(LootContextParams.THIS_ENTITY) instanceof Player p) {
//            player = p;
//        } else if (context.hasParam(LootContextParams.LAST_DAMAGE_PLAYER) && context.getParam(LootContextParams.LAST_DAMAGE_PLAYER) instanceof Player p2) {
//            player = p2;
//        }
//        if (player == null || !player.hasEffect(ModEffects.BAKED_LUCK)) return generatedLoot;
//
//        // Check that the block is in the ore tag
//        BlockState state = context.getParam(LootContextParams.BLOCK_STATE);
//        if (state == null) return generatedLoot;
//
//        Block block = state.getBlock();
//        TagKey<Block> oresTag = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"));
//        if (!block.builtInRegistryHolder().is(oresTag)) return generatedLoot;
//
//
//        // Get the tool
//        ItemStack tool = context.getParam(LootContextParams.TOOL);
//
//        // Get Holder<Enchantment> for Fortune
//        Holder<net.minecraft.world.item.enchantment.Enchantment> fortuneHolder =
//                level.registryAccess()
//                        .registryOrThrow(Registries.ENCHANTMENT)
//                        .getHolder(Enchantments.FORTUNE)
//                        .orElseThrow(() -> new IllegalStateException("Fortune enchantment not found"));
//
//        int existingFortune = 0;
//        if (tool != null) {
//            existingFortune = ((IItemStackExtension) tool).getEnchantmentLevel(fortuneHolder);
//        }
//
//        int totalFortune = existingFortune + 5; // +5 from Baked Luck
//        RandomSource rng = context.getRandom();
//
//        // Apply extra drops
//        for (ItemStack stack : generatedLoot) {
//            if (totalFortune > 0) {
//                int extra = rng.nextInt(totalFortune + 1); // uniform bonus simulating Fortune
//                stack.grow(extra);
//            }
//        }
//
//        return generatedLoot;
//    }
//
//    @Override
//    public MapCodec<? extends IGlobalLootModifier> codec() {
//        return CODEC;
//    }
//}
