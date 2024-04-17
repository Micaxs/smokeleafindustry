package net.micaxs.smokeleafindustry.screen;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SmokeleafIndustryMod.MOD_ID);

    public static final RegistryObject<MenuType<HerbGrinderStationMenu>> HERB_GRINDER_MENU =
            registerMenuType("herb_grinder_menu", HerbGrinderStationMenu::new);

    public static final RegistryObject<MenuType<HerbExtractorMenu>> HERB_EXTRACTOR_MENU =
            registerMenuType("herb_extractor_menu", HerbExtractorMenu::new);

    public static final RegistryObject<MenuType<HerbGeneratorMenu>> HERB_GENERATOR_MENU =
            registerMenuType("herb_generator_menu", HerbGeneratorMenu::new);

    public static final RegistryObject<MenuType<HerbMutationMenu>> HERB_MUTATION_MENU  =
            registerMenuType("herb_mutation_menu", HerbMutationMenu::new);

    public static final RegistryObject<MenuType<HerbEvaporatorMenu>> HERB_EVAPORATOR_MENU  =
            registerMenuType("herb_evaporator_menu", HerbEvaporatorMenu::new);

    public static final RegistryObject<MenuType<HempSpinnerMenu>> HEMP_SPINNER_MENU  =
            registerMenuType("hemp_spinner_menu", HempSpinnerMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
