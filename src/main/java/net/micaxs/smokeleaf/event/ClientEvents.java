package net.micaxs.smokeleaf.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.micaxs.smokeleaf.effect.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID, value = Dist.CLIENT)
public class ClientEvents {


    // -------- Echo Location particles on sound --------
    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        LocalPlayer player = mc.player;
        if (level == null || player == null) return;
        if (!player.hasEffect(ModEffects.ECHO_LOCATION)) return;

        SoundInstance snd = event.getSound();
        if (snd == null) return;

        double sx, sy, sz;
        if (snd.isRelative()) {
            var cam = mc.gameRenderer.getMainCamera().getPosition();
            sx = cam.x + snd.getX();
            sy = cam.y + snd.getY();
            sz = cam.z + snd.getZ();
        } else {
            sx = snd.getX();
            sy = snd.getY();
            sz = snd.getZ();
        }
        if (!Double.isFinite(sx) || !Double.isFinite(sy) || !Double.isFinite(sz)) return;

        int count = 12;
        double base = 0.14 + (snd.getVolume() * 0.06);
        for (int i = 0; i < count; i++) {
            double ang = (Math.PI * 2.0) * i / count;
            double jitter = level.random.nextDouble() * 0.02;
            double vx = Math.cos(ang) * (base + jitter);
            double vz = Math.sin(ang) * (base + jitter);
            level.addParticle(ModParticles.ECHO_LOCATION_PARTICLE.get(), sx, sy, sz, vx, 0.0, vz);
        }
    }




    // -------- Friend or Foe spoofed name tags --------
    @SubscribeEvent
    public static void onRenderName(RenderNameTagEvent event) {
        LocalPlayer viewer = Minecraft.getInstance().player;
        if (viewer == null || !viewer.hasEffect(ModEffects.FRIEND_OR_FOE)) return;

        Entity e = event.getEntity();
        String[] pool = new String[]{"Zombie", "Villager", "Creeper", "Cow", "Sheep", "Enderman", "Pig", "Spider"};
        int idx = Math.floorMod(e.getUUID().hashCode(), pool.length);
        event.setContent(net.minecraft.network.chat.Component.literal(pool[idx]));
    }




    // -------- Linguist's High: client-side villager interact subtitle --------
    private static final Random RAND = new Random();

    @SubscribeEvent
    public static void onClientInteractEntity(PlayerInteractEvent.EntityInteract evt) {
        if (evt.getEntity().level().isClientSide
                && evt.getTarget() instanceof Villager
                && evt.getEntity().hasEffect(ModEffects.LINGUISTS_HIGH)) {
            showLinguistSubtitle();
        }
    }

    private static void showLinguistSubtitle() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.hasEffect(ModEffects.LINGUISTS_HIGH)) return;

        int variant = 1 + RAND.nextInt(11);
        var text = net.minecraft.network.chat.Component.translatable("linguist.smokeleafindustries.villager_text." + variant);
        mc.gui.setOverlayMessage(text, false);
    }




    // -------- Relaxed: camera sway --------
    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(ModEffects.RELAXED)) {
            float tick = (player.tickCount % 360);
            event.setYaw(event.getYaw() + (float) Math.sin(tick * 0.01) * 1.5F);
            event.setPitch(event.getPitch() + (float) Math.cos(tick * 0.01) * 1.5F);
        }
    }




    // -------- Zombified: render player as a zombie --------
    private static final Map<UUID, Zombie> CACHE = new WeakHashMap<>();

    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre event) {
        LivingEntity le = event.getEntity();
        if (!(le instanceof Player player)) return;
        if (!player.hasEffect(ModEffects.ZOMBIFIED)) return;

        event.setCanceled(true);

        float pt = event.getPartialTick();
        PoseStack pose = event.getPoseStack();
        MultiBufferSource buf = event.getMultiBufferSource();
        int light = event.getPackedLight();

        Zombie fake = CACHE.computeIfAbsent(player.getUUID(), id -> new Zombie(player.level()));
        fake.xo = player.xo;
        fake.yo = player.yo;
        fake.zo = player.zo;
        fake.setPos(player.getX(), player.getY(), player.getZ());

        float bodyYaw = Mth.lerp(pt, player.yBodyRotO, player.yBodyRot);
        float headYaw = Mth.lerp(pt, player.yHeadRotO, player.yHeadRot);
        float entityYaw = bodyYaw;

        fake.yBodyRotO = bodyYaw;
        fake.yBodyRot = bodyYaw;
        fake.yHeadRotO = headYaw;
        fake.yHeadRot = headYaw;
        fake.yRotO = entityYaw;
        fake.setYRot(entityYaw);

        float pitch = Mth.lerp(pt, player.xRotO, player.getXRot());
        fake.xRotO = pitch;
        fake.setXRot(pitch);

        fake.setAggressive(false);
        fake.setNoAi(true);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            var stack = player.getItemBySlot(slot);
            fake.setItemSlot(slot, stack);
        }

        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher disp = mc.getEntityRenderDispatcher();
        @SuppressWarnings("unchecked")
        LivingEntityRenderer<Zombie, ?> renderer =
                (LivingEntityRenderer<Zombie, ?>) disp.getRenderer(fake);

        renderer.render(fake, entityYaw, pt, pose, buf, light);
    }
}
