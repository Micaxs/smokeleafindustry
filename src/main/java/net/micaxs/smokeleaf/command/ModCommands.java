package net.micaxs.smokeleaf.command;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public final class ModCommands {

    private ModCommands() {}

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        DNADebugCommand.register(event.getDispatcher());
    }
}