package net.micaxs.smokeleafindustry.item;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    HEMP_FABRIC_RED("hempfabric_red", 26, new int[]{ 1, 3, 2, 1 }, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0f, () -> Ingredient.of(ModItems.HEMP_FABRIC.get())),
    HEMP_FABRIC_GREEN("hempfabric_green", 26, new int[]{ 1, 3, 2, 1 }, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0f, () -> Ingredient.of(ModItems.HEMP_FABRIC.get())),
    HEMP_FABRIC_YELLOW("hempfabric_yellow", 26, new int[]{ 1, 3, 2, 1 }, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0f, () -> Ingredient.of(ModItems.HEMP_FABRIC.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = new int[]{11, 16, 15, 13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return SmokeleafIndustryMod.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
