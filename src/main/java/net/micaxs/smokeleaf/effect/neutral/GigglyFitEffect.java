package net.micaxs.smokeleaf.effect.neutral;

import net.micaxs.smokeleaf.network.GigglyFitPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Random;

public class GigglyFitEffect extends MobEffect {
    private static final int MIN_DELAY_TICKS = 15; // 2 seconds
    private static final int MAX_DELAY_TICKS = 25; // 3 seconds

    public GigglyFitEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide() && entity instanceof ServerPlayer player) {
            var data = entity.getPersistentData();
            long nextJumpTime = data.getLong("GigglyFitNextJump");
            long worldTime = entity.level().getGameTime();

            if (worldTime >= nextJumpTime) {
                PacketDistributor.sendToPlayer(player, new GigglyFitPayload(true));
                RandomSource rand = entity.getRandom();
                long delay = MIN_DELAY_TICKS + rand.nextInt(MAX_DELAY_TICKS - MIN_DELAY_TICKS + 1);
                data.putLong("GigglyFitNextJump", worldTime + delay);
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    /** Client-side jump execution */
    public static void performJumpClient() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        if (player.onGround()) {
            // full jump
            player.jumpFromGround();

            // play slime jump sound
            player.playSound(SoundEvents.SLIME_JUMP, 1.0f, 1.0f);

            // spawn particles around player
            for (int i = 0; i < 6; i++) {
                double offsetX = (Math.random() - 0.5) * 0.5;
                double offsetY = 0.1 + Math.random() * 0.3;
                double offsetZ = (Math.random() - 0.5) * 0.5;
                player.level().addParticle(ParticleTypes.HAPPY_VILLAGER,
                        player.getX() + offsetX,
                        player.getY() + 0.1,
                        player.getZ() + offsetZ,
                        0, offsetY, 0);
            }
        }
    }
}
