package net.micaxs.smokeleafindustry.event;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.item.custom.BaseBudItem;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;


@Mod.EventBusSubscriber(modid = SmokeleafIndustryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemColor(RegisterColorHandlersEvent.Item event) {
        ItemColor itemColor = (stack, tintIndex) -> {
            if (stack.getItem() instanceof BaseBudItem) {
                BaseBudItem budItem = (BaseBudItem) stack.getItem();
                CompoundTag nbt = stack.getTag();
                if (nbt != null && nbt.contains("dry") && nbt.getInt("dry") == 1) {
                    return 0xD6CEC3;
                }
            }
            return -1;
        };

        // Register the color handler for each item
        event.getItemColors().register(itemColor, ModItems.WHITE_WIDOW_BUD.get());
        event.getItemColors().register(itemColor, ModItems.BUBBLE_KUSH_BUD.get());
        event.getItemColors().register(itemColor, ModItems.LEMON_HAZE_BUD.get());
        event.getItemColors().register(itemColor, ModItems.SOUR_DIESEL_BUD.get());
        event.getItemColors().register(itemColor, ModItems.BLUE_ICE_BUD.get());
        event.getItemColors().register(itemColor, ModItems.BUBBLEGUM_BUD.get());
        event.getItemColors().register(itemColor, ModItems.PURPLE_HAZE_BUD.get());
    }
}
