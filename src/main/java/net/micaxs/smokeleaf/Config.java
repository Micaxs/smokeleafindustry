package net.micaxs.smokeleaf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.electronwill.nightconfig.core.UnmodifiableConfig;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // plant_nutrients is a list of NightConfig tables (NOT java.util.Map).
    // Example TOML:
    // plant_nutrients = [
    //   { "white_widow_crop" = { n = 7, p = 6, k = 13 } }
    // ]
    public static final ModConfigSpec.ConfigValue<List<? extends UnmodifiableConfig>> PLANT_NUTRIENTS =
            BUILDER
                    .comment(
                            "Per-plant nutrient targets as a list of tables.",
                            "Example:",
                            "plant_nutrients = [ { \"white_widow_crop\" = { n = 7, p = 6, k = 13 } } ]",
                            "You can also use full IDs like \"smokeleafindustry:white_widow_crop\""
                    )
                    .defineListAllowEmpty(
                            "plant_nutrients",
                            defaultPlantNutrients(),
                            Config::validatePlantNutrientsElement
                    );

    static final ModConfigSpec SPEC = BUILDER.build();

    // Cache resolved targets for quick lookup; keys include both "namespace:path" and plain "path".
    private static final Map<String, NutrientTarget> PLANT_NUTRIENTS_CACHE = new HashMap<>();

    public static final class NutrientTarget {
        public final int n;
        public final int p;
        public final int k;

        public NutrientTarget(int n, int p, int k) {
            this.n = n;
            this.p = p;
            this.k = k;
        }
    }

    // Defaults using NightConfig in-memory tables so the writer can serialize them.
    private static List<UnmodifiableConfig> defaultPlantNutrients() {
        var wwInner = com.electronwill.nightconfig.core.Config.inMemory();
        wwInner.set("n", 7);
        wwInner.set("p", 6);
        wwInner.set("k", 13);

        var wwOuter = com.electronwill.nightconfig.core.Config.inMemory();
        wwOuter.set("white_widow_crop", wwInner);

        return List.of(wwOuter);
    }

    private static boolean validatePlantNutrientsElement(Object o) {
        if (!(o instanceof UnmodifiableConfig outer)) return false;
        Map<String, Object> outerMap = outer.valueMap();
        if (outerMap.isEmpty() || outerMap.size() != 1) return false;

        var entry = outerMap.entrySet().iterator().next();
        Object innerObj = entry.getValue();
        if (!(innerObj instanceof UnmodifiableConfig inner)) return false;

        Object n = inner.get("n");
        Object p = inner.get("p");
        Object k = inner.get("k");
        return isIntLike(n) && isIntLike(p) && isIntLike(k);
    }

    private static boolean isIntLike(Object o) {
        if (o instanceof Number) return true;
        if (o instanceof String s) {
            try { Integer.parseInt(s); return true; } catch (NumberFormatException ignored) {}
        }
        return false;
    }

    private static int toInt(Object o, int def) {
        if (o instanceof Number num) return num.intValue();
        if (o instanceof String s) {
            try { return Integer.parseInt(s); } catch (NumberFormatException ignored) {}
        }
        return def;
    }

    private static void rebuildPlantNutrientsCache() {
        PLANT_NUTRIENTS_CACHE.clear();
        List<? extends UnmodifiableConfig> list = PLANT_NUTRIENTS.get();
        if (list == null) return;

        for (UnmodifiableConfig outer : list) {
            if (outer == null) continue;
            Map<String, Object> outerMap = outer.valueMap();
            if (outerMap.isEmpty()) continue;

            var e = outerMap.entrySet().iterator().next();
            String key = e.getKey(); // may be "namespace:path" or just "path"
            Object innerObj = e.getValue();
            if (!(innerObj instanceof UnmodifiableConfig inner)) continue;

            int n = toInt(inner.get("n"), 0);
            int p = toInt(inner.get("p"), 0);
            int k = toInt(inner.get("k"), 0);
            NutrientTarget target = new NutrientTarget(n, p, k);

            // Keep exactly as provided
            PLANT_NUTRIENTS_CACHE.put(key, target);

            // If the key is a full RL, also index by its path for convenience
            try {
                ResourceLocation rl = ResourceLocation.parse(key);
                PLANT_NUTRIENTS_CACHE.putIfAbsent(rl.getPath(), target);
            } catch (Exception ignored) {
                // If the key is just a path, that's fine.
            }
        }
    }

    public static Optional<NutrientTarget> getNutrientTargetFor(ResourceLocation id) {
        if (id == null) return Optional.empty();
        NutrientTarget t = PLANT_NUTRIENTS_CACHE.get(id.toString());
        if (t != null) return Optional.of(t);
        t = PLANT_NUTRIENTS_CACHE.get(id.getPath());
        return Optional.ofNullable(t);
    }

    @SubscribeEvent
    static void onConfigLoadOrReload(final ModConfigEvent event) {
        rebuildPlantNutrientsCache();
    }
}
