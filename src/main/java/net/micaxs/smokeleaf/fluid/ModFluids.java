package net.micaxs.smokeleaf.fluid;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, SmokeleafIndustries.MODID);

    // Hemp Oil Fluid
    public static final Supplier<FlowingFluid> SOURCE_HEMP_OIL_FLUID = FLUIDS.register("hemp_oil_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.HEMP_OIL_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_HEMP_OIL_FLUID = FLUIDS.register("flowing_hemp_oil",
            () -> new BaseFlowingFluid.Flowing(ModFluids.HEMP_OIL_FLUID_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> HEMP_OIL_FLUID_BLOCK = ModBlocks.BLOCKS.register("hemp_oil_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_HEMP_OIL_FLUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> HEMP_OIL_BUCKET = ModItems.ITEMS.registerItem("hemp_oil_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_HEMP_OIL_FLUID.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final BaseFlowingFluid.Properties HEMP_OIL_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.HEMP_OIL_FLUID_TYPE, SOURCE_HEMP_OIL_FLUID, FLOWING_HEMP_OIL_FLUID)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2);

    // Hash Oil Fluid
    public static final Supplier<FlowingFluid> SOURCE_HASH_OIL_FLUID = FLUIDS.register("hash_oil_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.HASH_OIL_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_HASH_OIL_FLUID = FLUIDS.register("flowing_hash_oil",
            () -> new BaseFlowingFluid.Flowing(ModFluids.HASH_OIL_FLUID_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> HASH_OIL_FLUID_BLOCK = ModBlocks.BLOCKS.register("hash_oil_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_HASH_OIL_FLUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> HASH_OIL_BUCKET = ModItems.ITEMS.registerItem("hash_oil_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_HASH_OIL_FLUID.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final BaseFlowingFluid.Properties HASH_OIL_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.HASH_OIL_FLUID_TYPE, SOURCE_HASH_OIL_FLUID, FLOWING_HASH_OIL_FLUID)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2);

    // Hash Oil Sludge Fluid
    public static final Supplier<FlowingFluid> SOURCE_HASH_OIL_SLUDGE_FLUID = FLUIDS.register("hash_oil_sludge_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.HASH_OIL_SLUDGE_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_HASH_OIL_SLUDGE_FLUID = FLUIDS.register("flowing_hash_oil_sludge",
            () -> new BaseFlowingFluid.Flowing(ModFluids.HASH_OIL_SLUDGE_FLUID_PROPERTIES));
    public static final DeferredBlock<LiquidBlock> HASH_OIL_SLUDGE_FLUID_BLOCK = ModBlocks.BLOCKS.register("hash_oil_sludge_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_HASH_OIL_SLUDGE_FLUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> HASH_OIL_SLUDGE_BUCKET = ModItems.ITEMS.registerItem("hash_oil_sludge_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_HASH_OIL_SLUDGE_FLUID.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final BaseFlowingFluid.Properties HASH_OIL_SLUDGE_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.HASH_OIL_SLUDGE_FLUID_TYPE, SOURCE_HASH_OIL_SLUDGE_FLUID, FLOWING_HASH_OIL_SLUDGE_FLUID)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2);

    // Late wiring to avoid null bucket/block during properties construction
    static {
        HASH_OIL_FLUID_PROPERTIES.block(HASH_OIL_FLUID_BLOCK).bucket(HASH_OIL_BUCKET);
        HASH_OIL_SLUDGE_FLUID_PROPERTIES.block(HASH_OIL_SLUDGE_FLUID_BLOCK).bucket(HASH_OIL_SLUDGE_BUCKET);
        HEMP_OIL_FLUID_PROPERTIES.block(HEMP_OIL_FLUID_BLOCK).bucket(HEMP_OIL_BUCKET);
    }

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

}
