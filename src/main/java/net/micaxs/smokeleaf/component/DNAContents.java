package net.micaxs.smokeleaf.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public record DNAContents(ItemStack slot0, ItemStack slot1, ItemStack slot2) {

    public static final DNAContents EMPTY = new DNAContents(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);

    public static final Codec<DNAContents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("slot0").orElse(ItemStack.EMPTY).forGetter(DNAContents::slot0),
            ItemStack.CODEC.fieldOf("slot1").orElse(ItemStack.EMPTY).forGetter(DNAContents::slot1),
            ItemStack.CODEC.fieldOf("slot2").orElse(ItemStack.EMPTY).forGetter(DNAContents::slot2)
    ).apply(instance, DNAContents::new));

    public boolean isFull() {
        return !slot0.isEmpty() && !slot1.isEmpty() && !slot2.isEmpty();
    }

    public ItemStack get(int index) {
        return switch (index) {
            case 0 -> slot0;
            case 1 -> slot1;
            case 2 -> slot2;
            default -> ItemStack.EMPTY;
        };
    }

    public DNAContents with(int index, ItemStack stack) {
        return switch (index) {
            case 0 -> new DNAContents(stack, slot1, slot2);
            case 1 -> new DNAContents(slot0, stack, slot2);
            case 2 -> new DNAContents(slot0, slot1, stack);
            default -> this;
        };
    }
}