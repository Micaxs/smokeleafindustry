package net.micaxs.smokeleaf.datagen;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.fluid.ModFluids;
import net.micaxs.smokeleaf.item.ModItems;
import net.micaxs.smokeleaf.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BASE_EXTRACT.get())
                .requires(Items.MILK_BUCKET)
                .requires(Items.SUGAR)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INFUSED_BUTTER.get())
                .requires(ModItems.BUTTER)
                .requires(ModTags.WEEDS)
                .unlockedBy(getHasName(ModItems.BUTTER), has(ModItems.BUTTER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HEMP_PLASTIC.get(), 4)
                .requires(ModFluids.HEMP_OIL_BUCKET)
                .requires(ModItems.BIO_COMPOSITE)
                .unlockedBy(getHasName(ModItems.BIO_COMPOSITE), has(ModItems.BIO_COMPOSITE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HEMP_COAL.get())
                .requires(Items.COAL)
                .requires(ModItems.HEMP_LEAF, 2)
                .unlockedBy(getHasName(ModItems.HEMP_LEAF), has(ModItems.HEMP_LEAF))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REFLECTOR.get())
                .pattern(" I ")
                .pattern("IRI")
                .define('I', ModItems.HEMP_PLASTIC)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ModItems.HEMP_PLASTIC), has(ModItems.HEMP_PLASTIC))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HPS_LAMP.get())
                .pattern("PPP")
                .pattern("RGP")
                .pattern("PPP")
                .define('P', Items.GLASS_PANE)
                .define('R', Items.REDSTONE)
                .define('G', Items.GLOWSTONE)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMPTY_BAG.get())
                .pattern("I I")
                .pattern(" I ")
                .define('I', ModItems.HEMP_PLASTIC)
                .unlockedBy(getHasName(ModItems.HEMP_PLASTIC), has(ModItems.HEMP_PLASTIC))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HEMP_PLANKS.get(), 8)
                .pattern("PPP")
                .pattern("PBP")
                .pattern("PPP")
                .define('P', Items.OAK_PLANKS)
                .define('B', ModFluids.HEMP_OIL_BUCKET)
                .unlockedBy(getHasName(ModItems.HEMP_PLASTIC), has(ModItems.HEMP_PLASTIC))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HEMP_STONE.get(), 8)
                .pattern("PPP")
                .pattern("PBP")
                .pattern("PPP")
                .define('P', Items.STONE)
                .define('B', ModFluids.HEMP_OIL_BUCKET)
                .unlockedBy(getHasName(ModItems.HEMP_PLASTIC), has(ModItems.HEMP_PLASTIC))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HEMP_BRICKS.get(), 4)
                .pattern("PP")
                .pattern("PP")
                .define('P', ModBlocks.HEMP_STONE)
                .unlockedBy(getHasName(ModItems.HEMP_PLASTIC), has(ModItems.HEMP_PLASTIC))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MANUAL_GRINDER.get())
                .pattern("SHS")
                .pattern("ISI")
                .pattern("SHS")
                .define('S', Items.STONE)
                .define('H', ModItems.HEMP_FIBERS)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.HEMP_FIBERS), has(ModItems.HEMP_FIBERS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DRYING_RACK.get())
                .pattern("SHS")
                .pattern("SPS")
                .pattern("SHS")
                .define('S', Items.IRON_INGOT)
                .define('H', ModItems.HEMP_FIBERS)
                .define('P', Items.OAK_PLANKS)
                .unlockedBy(getHasName(ModItems.HEMP_FIBERS), has(ModItems.HEMP_FIBERS))
                .save(recipeOutput);

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.BIO_COMPOSITE.get()),
                        RecipeCategory.MISC,
                        ModItems.HEMP_PLASTIC.get(),
                        0.15f,
                        320)
                .unlockedBy(getHasName(ModItems.BIO_COMPOSITE), has(ModItems.BIO_COMPOSITE))
                .save(recipeOutput, SmokeleafIndustries.MODID + ":smelting/hemp_plastic_from_bio_composite");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WEED_COOKIE.get())
                .requires(Items.COOKIE)
                .requires(ModItems.INFUSED_BUTTER)
                .unlockedBy(getHasName(ModItems.INFUSED_BUTTER), has(ModItems.INFUSED_BUTTER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HASH_BROWNIE.get())
                .pattern("WBW")
                .pattern("CHC")
                .pattern("WBW")
                .define('C', Items.COCOA_BEANS)
                .define('W', ModTags.WEEDS)
                .define('B', ModItems.INFUSED_BUTTER)
                .define('H', ModFluids.HASH_OIL_BUCKET)
                .unlockedBy(getHasName(ModFluids.HASH_OIL_BUCKET), has(ModFluids.HASH_OIL_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HERB_CAKE.get())
                .pattern("BSB")
                .pattern("WEW")
                .pattern("MMM")
                .define('E', Items.EGG)
                .define('S', Items.SUGAR)
                .define('W', ModTags.WEEDS)
                .define('B', ModItems.INFUSED_BUTTER)
                .define('M', Items.MILK_BUCKET)
                .unlockedBy(getHasName(ModItems.INFUSED_BUTTER), has(ModItems.INFUSED_BUTTER))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEMP_STICK.get())
                .pattern("  F")
                .pattern(" S ")
                .pattern("F  ")
                .define('S', Items.STICK)
                .define('F', ModItems.HEMP_FIBERS)
                .unlockedBy(getHasName(ModItems.HEMP_FIBERS), has(ModItems.HEMP_FIBERS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "hemp_stick"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEMP_STICK.get())
                .pattern("F  ")
                .pattern(" S ")
                .pattern("  F")
                .define('S', Items.STICK)
                .define('F', ModItems.HEMP_FIBERS)
                .unlockedBy(getHasName(ModItems.HEMP_FIBERS), has(ModItems.HEMP_FIBERS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "hemp_stick_alt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEMP_HAMMER.get())
                .pattern(" CF")
                .pattern(" SC")
                .pattern("S  ")
                .define('S', ModItems.HEMP_STICK)
                .define('F', ModItems.HEMP_FIBERS)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.HEMP_FIBERS), has(ModItems.HEMP_FIBERS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "hemp_hammer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEMP_STICK.get())
                .pattern("P")
                .pattern("P")
                .define('P', ModBlocks.HEMP_PLANKS)
                .unlockedBy(getHasName(ModBlocks.HEMP_PLANKS), has(ModBlocks.HEMP_PLANKS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "hemp_stick_from_planks"));


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BIO_COMPOSITE.get())
                .requires(ModItems.HEMP_HAMMER)
                .requires(ModItems.HEMP_LEAF, 3)
                .requires(ModItems.HEMP_FIBERS, 1)
                .unlockedBy(getHasName(ModItems.HEMP_HAMMER), has(ModItems.HEMP_HAMMER))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "hemp_plastic_from_hammering"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNFINISHED_HEMP_CORE.get())
                .pattern("RAR")
                .pattern("GDG")
                .pattern("RAR")
                .define('R', Items.REDSTONE)
                .define('G', Items.GLOWSTONE_DUST)
                .define('A', Items.AMETHYST_SHARD)
                .define('D', Items.DIAMOND)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "unfinished_hemp_core"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HEMP_CORE.get())
                .requires(ModItems.HEMP_HAMMER)
                .requires(ModItems.HEMP_PLASTIC)
                .requires(ModItems.UNFINISHED_HEMP_CORE)
                .unlockedBy(getHasName(ModItems.UNFINISHED_HEMP_CORE), has(ModItems.UNFINISHED_HEMP_CORE))
                .save(recipeOutput);


        // Machine Recipes
        // Generator
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GENERATOR.get())
                .pattern("HIH")
                .pattern("CRC")
                .pattern("HIH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', Items.IRON_INGOT)
                .define('R', Items.FURNACE)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);

        // Grinder
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GRINDER.get())
                .pattern("HIH")
                .pattern("CGC")
                .pattern("HIH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GRINDSTONE)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);

        // Extractor
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRACTOR.get())
                .pattern("HCH")
                .pattern("IGI")
                .pattern("HCH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GLASS_BOTTLE)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);

        // Liquifier
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LIQUIFIER.get())
                .pattern("HCH")
                .pattern("IBI")
                .pattern("HCH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', Items.GOLD_INGOT)
                .define('B', Items.BUCKET)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);

        // Mutator
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MUTATOR.get())
                .pattern("HCH")
                .pattern("IGI")
                .pattern("HCH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', ModItems.HEMP_STICK)
                .define('G', Items.DIAMOND_BLOCK)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);

        // Synthesizer
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SYNTHESIZER.get())
                .pattern("HCH")
                .pattern("IGI")
                .pattern("HCH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', Items.DIAMOND)
                .define('G', Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);

        // Sequencer
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SEQUENCER.get())
                .pattern("HCH")
                .pattern("IGI")
                .pattern("HCH")
                .define('H', ModItems.HEMP_PLASTIC)
                .define('C', ModItems.HEMP_CORE)
                .define('I', Items.GLASS_BOTTLE)
                .define('G', Items.GLOWSTONE)
                .unlockedBy(getHasName(ModItems.HEMP_CORE), has(ModItems.HEMP_CORE))
                .save(recipeOutput);




        // Stonecutting Recipes
        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ModBlocks.HEMP_STONE.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.HEMP_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.HEMP_STONE.get()), has(ModBlocks.HEMP_STONE.get()))
                .save(recipeOutput, "smokeleafindustries:stonecutting/hemp_bricks_from_hemp_stone");

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModBlocks.HEMP_STONE.get()),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.HEMP_CHISELED_STONE.get())
                .unlockedBy(getHasName(ModBlocks.HEMP_STONE.get()), has(ModBlocks.HEMP_STONE.get()))
                .save(recipeOutput, "smokeleafindustries:stonecutting/hemp_chiseled_stone_from_hemp_stone");

        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ModBlocks.HEMP_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.HEMP_CHISELED_STONE.get())
                .unlockedBy(getHasName(ModBlocks.HEMP_BRICKS.get()), has(ModBlocks.HEMP_BRICKS.get()))
                .save(recipeOutput, "smokeleafindustries:stonecutting/hemp_chiseled_stone_from_hemp_bricks");


        // Filled Bag Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WHITE_WIDOW_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.WHITE_WIDOW_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BUBBLE_KUSH_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.BUBBLE_KUSH_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LEMON_HAZE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.LEMON_HAZE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUR_DIESEL_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.SOUR_DIESEL_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLUE_ICE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.BLUE_ICE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BUBBLEGUM_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.BUBBLEGUM_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PURPLE_HAZE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.PURPLE_HAZE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OG_KUSH_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.OG_KUSH_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JACK_HERER_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.JACK_HERER_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GARY_PEYTON_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.GARY_PEYTON_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMNESIA_HAZE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.AMNESIA_HAZE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AK47_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.AK47_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GHOST_TRAIN_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.GHOST_TRAIN_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GRAPE_APE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.GRAPE_APE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COTTON_CANDY_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.COTTON_CANDY_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BANANA_KUSH_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.BANANA_KUSH_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CARBON_FIBER_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.CARBON_FIBER_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BIRTHDAY_CAKE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.BIRTHDAY_CAKE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLUE_COOKIES_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.BLUE_COOKIES_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AFGHANI_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.AFGHANI_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOONBOW_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.MOONBOW_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LAVA_CAKE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.LAVA_CAKE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JELLY_RANCHER_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.JELLY_RANCHER_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STRAWBERRY_SHORTCAKE_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.STRAWBERRY_SHORTCAKE_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PINK_KUSH_BAG.get())
                .pattern("BWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('B', ModItems.EMPTY_BAG)
                .define('W', ModItems.PINK_KUSH_WEED)
                .unlockedBy(getHasName(ModItems.EMPTY_BAG), has(ModItems.EMPTY_BAG))
                .save(recipeOutput);

        // Filled bag to Weed Recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHITE_WIDOW_WEED.get(), 8)
                .requires(ModItems.WHITE_WIDOW_BAG)
                .unlockedBy(getHasName(ModItems.WHITE_WIDOW_BAG), has(ModItems.WHITE_WIDOW_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BUBBLE_KUSH_WEED.get(), 8)
                .requires(ModItems.BUBBLE_KUSH_BAG)
                .unlockedBy(getHasName(ModItems.BUBBLE_KUSH_BAG), has(ModItems.BUBBLE_KUSH_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LEMON_HAZE_WEED.get(), 8)
                .requires(ModItems.LEMON_HAZE_BAG)
                .unlockedBy(getHasName(ModItems.LEMON_HAZE_BAG), has(ModItems.LEMON_HAZE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SOUR_DIESEL_WEED.get(), 8)
                .requires(ModItems.SOUR_DIESEL_BAG)
                .unlockedBy(getHasName(ModItems.SOUR_DIESEL_BAG), has(ModItems.SOUR_DIESEL_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLUE_ICE_WEED.get(), 8)
                .requires(ModItems.BLUE_ICE_BAG)
                .unlockedBy(getHasName(ModItems.BLUE_ICE_BAG), has(ModItems.BLUE_ICE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BUBBLEGUM_WEED.get(), 8)
                .requires(ModItems.BUBBLEGUM_BAG)
                .unlockedBy(getHasName(ModItems.BUBBLEGUM_BAG), has(ModItems.BUBBLEGUM_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PURPLE_HAZE_WEED.get(), 8)
                .requires(ModItems.PURPLE_HAZE_BAG)
                .unlockedBy(getHasName(ModItems.PURPLE_HAZE_BAG), has(ModItems.PURPLE_HAZE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.OG_KUSH_WEED.get(), 8)
                .requires(ModItems.OG_KUSH_BAG)
                .unlockedBy(getHasName(ModItems.OG_KUSH_BAG), has(ModItems.OG_KUSH_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JACK_HERER_WEED.get(), 8)
                .requires(ModItems.JACK_HERER_BAG)
                .unlockedBy(getHasName(ModItems.JACK_HERER_BAG), has(ModItems.JACK_HERER_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GARY_PEYTON_WEED.get(), 8)
                .requires(ModItems.GARY_PEYTON_BAG)
                .unlockedBy(getHasName(ModItems.GARY_PEYTON_BAG), has(ModItems.GARY_PEYTON_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AMNESIA_HAZE_WEED.get(), 8)
                .requires(ModItems.AMNESIA_HAZE_BAG)
                .unlockedBy(getHasName(ModItems.AMNESIA_HAZE_BAG), has(ModItems.AMNESIA_HAZE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AK47_WEED.get(), 8)
                .requires(ModItems.AK47_BAG)
                .unlockedBy(getHasName(ModItems.AK47_BAG), has(ModItems.AK47_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GHOST_TRAIN_WEED.get(), 8)
                .requires(ModItems.GHOST_TRAIN_BAG)
                .unlockedBy(getHasName(ModItems.GHOST_TRAIN_BAG), has(ModItems.GHOST_TRAIN_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GRAPE_APE_WEED.get(), 8)
                .requires(ModItems.GRAPE_APE_BAG)
                .unlockedBy(getHasName(ModItems.GRAPE_APE_BAG), has(ModItems.GRAPE_APE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COTTON_CANDY_WEED.get(), 8)
                .requires(ModItems.COTTON_CANDY_BAG)
                .unlockedBy(getHasName(ModItems.COTTON_CANDY_BAG), has(ModItems.COTTON_CANDY_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BANANA_KUSH_WEED.get(), 8)
                .requires(ModItems.BANANA_KUSH_BAG)
                .unlockedBy(getHasName(ModItems.BANANA_KUSH_BAG), has(ModItems.BANANA_KUSH_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CARBON_FIBER_WEED.get(), 8)
                .requires(ModItems.CARBON_FIBER_BAG)
                .unlockedBy(getHasName(ModItems.CARBON_FIBER_BAG), has(ModItems.CARBON_FIBER_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BIRTHDAY_CAKE_WEED.get(), 8)
                .requires(ModItems.BIRTHDAY_CAKE_BAG)
                .unlockedBy(getHasName(ModItems.BIRTHDAY_CAKE_BAG), has(ModItems.BIRTHDAY_CAKE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLUE_COOKIES_WEED.get(), 8)
                .requires(ModItems.BLUE_COOKIES_BAG)
                .unlockedBy(getHasName(ModItems.BLUE_COOKIES_BAG), has(ModItems.BLUE_COOKIES_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AFGHANI_WEED.get(), 8)
                .requires(ModItems.AFGHANI_BAG)
                .unlockedBy(getHasName(ModItems.AFGHANI_BAG), has(ModItems.AFGHANI_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOONBOW_WEED.get(), 8)
                .requires(ModItems.MOONBOW_BAG)
                .unlockedBy(getHasName(ModItems.MOONBOW_BAG), has(ModItems.MOONBOW_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LAVA_CAKE_WEED.get(), 8)
                .requires(ModItems.LAVA_CAKE_BAG)
                .unlockedBy(getHasName(ModItems.LAVA_CAKE_BAG), has(ModItems.LAVA_CAKE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JELLY_RANCHER_WEED.get(), 8)
                .requires(ModItems.JELLY_RANCHER_BAG)
                .unlockedBy(getHasName(ModItems.JELLY_RANCHER_BAG), has(ModItems.JELLY_RANCHER_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STRAWBERRY_SHORTCAKE_WEED.get(), 8)
                .requires(ModItems.STRAWBERRY_SHORTCAKE_BAG)
                .unlockedBy(getHasName(ModItems.STRAWBERRY_SHORTCAKE_BAG), has(ModItems.STRAWBERRY_SHORTCAKE_BAG))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PINK_KUSH_WEED.get(), 8)
                .requires(ModItems.PINK_KUSH_BAG)
                .unlockedBy(getHasName(ModItems.PINK_KUSH_BAG), has(ModItems.PINK_KUSH_BAG))
                .save(recipeOutput);


    }
}
