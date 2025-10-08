package net.micaxs.smokeleaf.loot;

import com.mojang.serialization.MapCodec;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.function.Supplier;

public class ModLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SmokeleafIndustries.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item", () -> AddItemModifier.CODEC);



//    // Direct reference to our BakedLuckModifier codec
//    public static final MapCodec<? extends IGlobalLootModifier> BAKED_LUCK_CODEC = BakedLuckModifier.CODEC;
//
//
//    public static void register(IEventBus bus) {
//        // Register the deferred register with the mod event bus
//        LOOT_MODIFIERS.register(bus);
//
//        LOOT_MODIFIERS.register("baked_luck", () -> BAKED_LUCK_CODEC);
//    }


    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

}