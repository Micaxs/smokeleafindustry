package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.component.ManualGrinderContents;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.item.custom.ManualGrinderItem;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class ManualGrinderEvents {

    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack result = event.getCrafting();
        if (!(result.getItem() instanceof ManualGrinderItem)) return;
        if (result.has(ModDataComponentTypes.MANUAL_GRINDER_CONTENTS.get())) return;

        Container inv = event.getInventory();
        ItemStack candidate = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slot = inv.getItem(i);
            if (slot.isEmpty()) continue;
            if (slot.getItem() instanceof ManualGrinderItem) continue;
            candidate = slot.copyWithCount(1);
            break;
        }

        if (!candidate.isEmpty()) {
            result.set(ModDataComponentTypes.MANUAL_GRINDER_CONTENTS.get(),
                    ManualGrinderContents.fromStack(candidate));
        }
    }
}