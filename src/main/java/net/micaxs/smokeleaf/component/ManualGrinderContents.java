package net.micaxs.smokeleaf.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public record ManualGrinderContents(ItemStack stack) {

    public static final Codec<ManualGrinderContents> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                    ItemStack.CODEC.fieldOf("stack").forGetter(ManualGrinderContents::stack)
            ).apply(inst, ManualGrinderContents::new));

    public static ManualGrinderContents fromStack(ItemStack stack) {
        return new ManualGrinderContents(stack.copy());
    }

    public ItemStack toStack() {
        return stack.copy();
    }
}