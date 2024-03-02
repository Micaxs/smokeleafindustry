package net.micaxs.smokeleafindustry.fluid;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.block.ModBlocks;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, SmokeleafIndustryMod.MOD_ID);


    public static final RegistryObject<FlowingFluid> SOURCE_HASH_OIL = FLUIDS.register("hash_oil_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.HASH_OIL_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_HASH_OIL = FLUIDS.register("flowing_hash_oil",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.HASH_OIL_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties HASH_OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.HASH_OIL_FLUID, SOURCE_HASH_OIL, FLOWING_HASH_OIL)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.HASH_OIL_BLOCK).bucket(ModItems.HASH_OIL_BUCKET);



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

}
