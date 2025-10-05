package net.micaxs.smokeleaf.block;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.custom.*;
import net.micaxs.smokeleaf.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SmokeleafIndustries.MODID);


    public static final DeferredBlock<Block> HEMP_STONE = registerBlock("hemp_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> HEMP_PLANKS = registerBlock("hemp_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> HEMP_BRICKS = registerBlock("hemp_bricks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> HEMP_CHISELED_STONE = registerBlock("hemp_chiseled_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.STONE)));


    public static final DeferredBlock<Block> LED_LIGHT = registerBlock("led_light",
            () -> new GrowLightBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .strength(1f).lightLevel(state -> state.getValue(GrowLightBlock.CLICKED) ? 15 : 0).noOcclusion().sound(SoundType.GLASS)));

    public static final DeferredBlock<Block> REFLECTOR = registerBlock("reflector",
            () -> new ReflectorBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .strength(1f).noOcclusion().sound(SoundType.METAL)
                    .lightLevel(state -> state.getValue(ReflectorBlock.HAS_LAMP) ? 15 : 0)));

    // Tobacco Crop
    public static final DeferredBlock<Block> TOBACCO_CROP = BLOCKS.register("tobacco_crop", () ->
            new TobaccoCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.TOBACCO_SEEDS));


    // Weed Crops
    public static final DeferredBlock<Block> HEMP_CROP = BLOCKS.register("hemp_crop", () ->
            new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.HEMP_SEEDS));
    public static final DeferredBlock<Block> WHITE_WIDOW_CROP = BLOCKS.register("white_widow_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.WHITE_WIDOW_SEEDS));
    public static final DeferredBlock<Block> BUBBLE_KUSH_CROP = BLOCKS.register("bubble_kush_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.BUBBLE_KUSH_SEEDS));
    public static final DeferredBlock<Block> LEMON_HAZE_CROP = BLOCKS.register("lemon_haze_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.LEMON_HAZE_SEEDS));
    public static final DeferredBlock<Block> SOUR_DIESEL_CROP = BLOCKS.register("sour_diesel_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.SOUR_DIESEL_SEEDS));
    public static final DeferredBlock<Block> BLUE_ICE_CROP = BLOCKS.register("blue_ice_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.BLUE_ICE_SEEDS));
    public static final DeferredBlock<Block> BUBBLEGUM_CROP = BLOCKS.register("bubblegum_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.BUBBLEGUM_SEEDS));
    public static final DeferredBlock<Block> PURPLE_HAZE_CROP = BLOCKS.register("purple_haze_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.PURPLE_HAZE_SEEDS));
    public static final DeferredBlock<Block> OG_KUSH_CROP = BLOCKS.register("og_kush_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.OG_KUSH_SEEDS));
    public static final DeferredBlock<Block> JACK_HERER_CROP = BLOCKS.register("jack_herer_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.JACK_HERER_SEEDS));
    public static final DeferredBlock<Block> GARY_PEYTON_CROP = BLOCKS.register("gary_peyton_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.GARY_PEYTON_SEEDS));
    public static final DeferredBlock<Block> AMNESIA_HAZE_CROP = BLOCKS.register("amnesia_haze_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.AMNESIA_HAZE_SEEDS));
    public static final DeferredBlock<Block> AK47_CROP = BLOCKS.register("ak47_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.AK47_SEEDS));
    public static final DeferredBlock<Block> GHOST_TRAIN_CROP = BLOCKS.register("ghost_train_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.GHOST_TRAIN_SEEDS));
    public static final DeferredBlock<Block> GRAPE_APE_CROP = BLOCKS.register("grape_ape_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.GRAPE_APE_SEEDS));
    public static final DeferredBlock<Block> COTTON_CANDY_CROP = BLOCKS.register("cotton_candy_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.COTTON_CANDY_SEEDS));
    public static final DeferredBlock<Block> BANANA_KUSH_CROP = BLOCKS.register("banana_kush_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.BANANA_KUSH_SEEDS));
    public static final DeferredBlock<Block> CARBON_FIBER_CROP = BLOCKS.register("carbon_fiber_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.CARBON_FIBER_SEEDS));
    public static final DeferredBlock<Block> BIRTHDAY_CAKE_CROP = BLOCKS.register("birthday_cake_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.BIRTHDAY_CAKE_SEEDS));
    public static final DeferredBlock<Block> BLUE_COOKIES_CROP = BLOCKS.register("blue_cookies_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.BLUE_COOKIES_SEEDS));
    public static final DeferredBlock<Block> AFGHANI_CROP = BLOCKS.register("afghani_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.AFGHANI_SEEDS));
    public static final DeferredBlock<Block> MOONBOW_CROP = BLOCKS.register("moonbow_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.MOONBOW_SEEDS));
    public static final DeferredBlock<Block> LAVA_CAKE_CROP = BLOCKS.register("lava_cake_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.LAVA_CAKE_SEEDS));
    public static final DeferredBlock<Block> JELLY_RANCHER_CROP = BLOCKS.register("jelly_rancher_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.JELLY_RANCHER_SEEDS));
    public static final DeferredBlock<Block> STRAWBERRY_SHORTCAKE_CROP = BLOCKS.register("strawberry_shortcake_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.STRAWBERRY_SHORTCAKE_SEEDS));
    public static final DeferredBlock<Block> PINK_KUSH_CROP = BLOCKS.register("pink_kush_crop", () ->
        new BaseWeedCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), ModItems.PINK_KUSH_SEEDS));



    // Machines
    public static final DeferredBlock<Block> GENERATOR = registerBlock("generator", () -> new GeneratorBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GRINDER = registerBlock("grinder", () -> new GrinderBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> EXTRACTOR = registerBlock("extractor", () -> new ExtractorBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LIQUIFIER = registerBlock("liquifier", () -> new LiquifierBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MUTATOR = registerBlock("mutator", () -> new MutatorBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SYNTHESIZER = registerBlock("synthesizer", () -> new SynthesizerBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SEQUENCER = registerBlock("sequencer", () -> new SequencerBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()));


    // Utility Blocks
    public static final DeferredBlock<Block> DRYING_RACK = registerBlock("drying_rack", () -> new DryingRackBlock(BlockBehaviour.Properties.of().strength(1f).noOcclusion().requiresCorrectToolForDrops()));



    // Helper Functions
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Register
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
