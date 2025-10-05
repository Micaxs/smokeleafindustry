//package net.micaxs.smokeleaf.event;
//
//import net.micaxs.smokeleaf.SmokeleafIndustries;
//import net.micaxs.smokeleaf.effect.ModEffects;
//import net.micaxs.smokeleaf.effect.beneficial.StonerSightEffect;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
//
//@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
//public class StonerSightEvents {

//    @SubscribeEvent
//    public static void onExpired(MobEffectEvent.Expired event) {
//        if (!(event.getEntity() instanceof Player player)) return;
//        if (event.getEffectInstance() != null
//                && event.getEffectInstance().getEffect() == ModEffects.STONER_SIGHT) { // compare Holders
//            cleanup(player);
//        }
//    }
//
//    @SubscribeEvent
//    public static void onRemoved(MobEffectEvent.Remove event) {
//        if (!(event.getEntity() instanceof Player player)) return;
//        if (event.getEffectInstance() != null
//                && event.getEffectInstance().getEffect() == ModEffects.STONER_SIGHT) { // compare Holders
//            cleanup(player);
//        }
//    }
//
//    private static void cleanup(LivingEntity entity) {
//        if (!(entity instanceof Player player)) return;
//        if (player.level().isClientSide()) return;
//
//        String playerTag = "stonersight:" + player.getUUID();
//        player.level().getEntitiesOfClass(
//                LivingEntity.class,
//                player.getBoundingBox().inflate(32.0D),
//                e -> e.getTags().contains(playerTag)
//        ).forEach(mob -> {
//            mob.removeEffect(MobEffects.GLOWING);
//            mob.removeTag(playerTag);
//        });
//    }
//}