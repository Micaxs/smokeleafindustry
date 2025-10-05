package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class CatUrineCollectionEvents {
    private static final String TAG_LAST_TIME = "SmokeleafIndustriesLastUrineCollect";
    private static final long COOLDOWN_TICKS = 5L * 60L * 20L; // 5 minutes


    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Cat cat)) return;

        Player player = event.getEntity();
        Level level = player.level();
        ItemStack held = player.getItemInHand(event.getHand());

        if (!held.is(Items.GLASS_BOTTLE)) return;
        if (level.isClientSide()) return;

        long now = level.getGameTime();
        long last = cat.getPersistentData().getLong(TAG_LAST_TIME);

        if (now - last < COOLDOWN_TICKS) {
            long remaining = COOLDOWN_TICKS - (now - last);
            long seconds = remaining / 20;
            player.displayClientMessage(
                    Component.translatable("message.smokeleafindustries.cat_urine_cooldown", seconds),
                    true
            );
            return;
        }

        // Cooldown passed
        cat.getPersistentData().putLong(TAG_LAST_TIME, now);

        if (!player.isCreative()) {
            held.shrink(1);
        }

        ItemStack result = new ItemStack(ModItems.CAT_URINE_BOTTLE.get());
        if (!player.addItem(result)) {
            player.drop(result, false);
        }

        level.playSound(null, cat.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1f, 1f);

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

}
