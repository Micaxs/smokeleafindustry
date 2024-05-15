package net.micaxs.smokeleafindustry.datagen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {

    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1f);
        trimMaterials.put(TrimMaterials.IRON, 0.2f);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3f);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4f);
        trimMaterials.put(TrimMaterials.COPPER, 0.5f);
        trimMaterials.put(TrimMaterials.GOLD, 0.6f);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7f);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8f);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9f);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0f);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SmokeleafIndustryMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.WHITE_WIDOW_SEEDS);
        simpleItem(ModItems.WHITE_WIDOW_BUD);
        simpleItem(ModItems.WHITE_WIDOW_WEED);
        simpleItem(ModItems.WHITE_WIDOW_EXTRACT);

        simpleItem(ModItems.BUBBLE_KUSH_SEEDS);
        simpleItem(ModItems.BUBBLE_KUSH_BUD);
        simpleItem(ModItems.BUBBLE_KUSH_WEED);
        simpleItem(ModItems.BUBBLE_KUSH_EXTRACT);

        simpleItem(ModItems.SOUR_DIESEL_SEEDS);
        simpleItem(ModItems.SOUR_DIESEL_BUD);
        simpleItem(ModItems.SOUR_DIESEL_WEED);
        simpleItem(ModItems.SOUR_DIESEL_EXTRACT);

        simpleItem(ModItems.BLUE_ICE_SEEDS);
        simpleItem(ModItems.BLUE_ICE_WEED);
        simpleItem(ModItems.BLUE_ICE_BUD);
        simpleItem(ModItems.BLUE_ICE_EXTRACT);

        simpleItem(ModItems.BUBBLEGUM_SEEDS);
        simpleItem(ModItems.BUBBLEGUM_BUD);
        simpleItem(ModItems.BUBBLEGUM_WEED);
        simpleItem(ModItems.BUBBLEGUM_EXTRACT);

        simpleItem(ModItems.PURPLE_HAZE_SEEDS);
        simpleItem(ModItems.PURPLE_HAZE_BUD);
        simpleItem(ModItems.PURPLE_HAZE_WEED);
        simpleItem(ModItems.PURPLE_HAZE_EXTRACT);

        simpleItem(ModItems.LEMON_HAZE_SEEDS);
        simpleItem(ModItems.LEMON_HAZE_BUD);
        simpleItem(ModItems.LEMON_HAZE_WEED);
        simpleItem(ModItems.LEMON_HAZE_EXTRACT);

        simpleItem(ModItems.EMPTY_BAG);
        simpleItem(ModItems.WHITE_WIDOW_BAG);
        simpleItem(ModItems.BUBBLE_KUSH_BAG);
        simpleItem(ModItems.LEMON_HAZE_BAG);
        simpleItem(ModItems.SOUR_DIESEL_BAG);
        simpleItem(ModItems.BLUE_ICE_BAG);
        simpleItem(ModItems.BUBBLEGUM_BAG);
        simpleItem(ModItems.PURPLE_HAZE_BAG);

        simpleItem(ModItems.JOINT);
        simpleItem(ModItems.BLUNT);
        simpleItem(ModItems.GRINDER);
        simpleItem(ModItems.HEMP_CORE);
        simpleItem(ModItems.HEMP_STICK);
        simpleItem(ModItems.BONG);
        simpleItem(ModItems.HEMP_FABRIC);
        simpleItem(ModItems.HEMP_FIBERS);
        simpleItem(ModItems.HEMP_LEAF);
        simpleItem(ModItems.HERB_CAKE);
        simpleItem(ModItems.HASH_BROWNIE);
        simpleItem(ModItems.WEED_COOKIE);

        simpleItem(ModItems.HASH_OIL_BUCKET);

        trimmedArmorItem(ModItems.HEMP_HELMET_RED);
        trimmedArmorItem(ModItems.HEMP_CHESTPLATE_RED);
        trimmedArmorItem(ModItems.HEMP_LEGGINGS_RED);
        trimmedArmorItem(ModItems.HEMP_BOOTS_RED);

        trimmedArmorItem(ModItems.HEMP_HELMET_GREEN);
        trimmedArmorItem(ModItems.HEMP_CHESTPLATE_GREEN);
        trimmedArmorItem(ModItems.HEMP_LEGGINGS_GREEN);
        trimmedArmorItem(ModItems.HEMP_BOOTS_GREEN);

        trimmedArmorItem(ModItems.HEMP_HELMET_YELLOW);
        trimmedArmorItem(ModItems.HEMP_CHESTPLATE_YELLOW);
        trimmedArmorItem(ModItems.HEMP_LEGGINGS_YELLOW);
        trimmedArmorItem(ModItems.HEMP_BOOTS_YELLOW);
    }

    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = SmokeleafIndustryMod.MOD_ID;

        if (itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
