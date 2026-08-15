package net.dan7zpc.enchantments_rekindled;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An dan7zpc config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    public static final ModConfigSpec.IntValue T1_HEAT_EXTRACTION_RATE = BUILDER
            .comment("tier one machine heat extraction rate")
            .defineInRange("t1_extraction_rate", 1, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T1_HEAT_CAPACITY = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t1_heat_capacity", 50, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T1_HEAT_ACCUMULATOR_MULTIPLIER = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t1_heat_accumulator_multiplier", 3, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T1_VENT_ACTIVATION_THRESHOLD = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t1_vent_activation_threshold", 250, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T1_VENT_DEACTIVATION_THRESHOLD = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t1_vent_deactivation_threshold", 100, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T2_HEAT_EXTRACTION_RATE = BUILDER
            .comment("tier one machine heat extraction rate")
            .defineInRange("t2_extraction_rate", 2, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T2_HEAT_CAPACITY = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t2_heat_capacity", 75, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T2_HEAT_ACCUMULATOR_MULTIPLIER = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t2_heat_accumulator_multiplier", 4, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T2_VENT_ACTIVATION_THRESHOLD = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t2_vent_activation_threshold", 250, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue T2_VENT_DEACTIVATION_THRESHOLD = BUILDER
            .comment("tier one machine heat capacity")
            .defineInRange("t2_vent_deactivation_threshold", 100, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);
    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
