package net.micaxs.smokeleaf.component;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record ManualGrinderContents(ItemStack stack) {
    public static ManualGrinderContents fromStack(ItemStack s) {
        return new ManualGrinderContents(s.copy());
    }

    public ItemStack toStack() {
        return stack.copy();
    }

    public static final Codec<ManualGrinderContents> CODEC =
            ItemStack.CODEC.xmap(ManualGrinderContents::new, ManualGrinderContents::stack);

    public static final StreamCodec<RegistryFriendlyByteBuf, ManualGrinderContents> STREAM_CODEC =
            ItemStack.STREAM_CODEC.map(ManualGrinderContents::new, ManualGrinderContents::stack);
}