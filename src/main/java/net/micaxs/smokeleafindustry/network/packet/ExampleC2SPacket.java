package net.micaxs.smokeleafindustry.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExampleC2SPacket {
    public ExampleC2SPacket() {

    }


    public ExampleC2SPacket(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server Side WHEE
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            //EntityType.COW.spawn(level, (ItemStack) null, null, player.blockPosition(), MobSpawnType.COMMAND, true, false);

        });
        return true;
    }
}
