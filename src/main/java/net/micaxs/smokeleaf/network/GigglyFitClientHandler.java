package net.micaxs.smokeleaf.network;

import net.micaxs.smokeleaf.effect.neutral.GigglyFitEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class GigglyFitClientHandler {
    public static void handle(GigglyFitPayload payload, IPayloadContext context) {
        if (!payload.jump()) return;

        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            LocalPlayer player = mc.player;
            if (player == null) return;

            // perform the jump with sound and particles
            GigglyFitEffect.performJumpClient();
        });
    }
}
