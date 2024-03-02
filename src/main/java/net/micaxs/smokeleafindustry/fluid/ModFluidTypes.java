package net.micaxs.smokeleafindustry.fluid;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {

    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");

    public static final ResourceLocation HASH_OIL_OVERLAY_RL = new ResourceLocation(SmokeleafIndustryMod.MOD_ID, "misc/in_hashoil_water");


    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SmokeleafIndustryMod.MOD_ID);


    public static final RegistryObject<FluidType> HASH_OIL_FLUID = register("hash_oil_fluid",
    FluidType.Properties.create().canExtinguish(true).lightLevel(3).density(15).viscosity(5).sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, HASH_OIL_OVERLAY_RL, 0xA1F2932E,
                new Vector3f( 172f / 255f, 85f / 255f, 207f / 255f ), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}
