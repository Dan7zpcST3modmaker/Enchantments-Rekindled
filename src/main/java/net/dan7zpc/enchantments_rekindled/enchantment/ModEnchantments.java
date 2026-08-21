package net.dan7zpc.enchantments_rekindled.enchantment;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        for (int i = 0; i < 26; i++) {
            register(context,
                    ResourceKey.create(Registries.ENCHANTMENT,
                            ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID, "galactic/"+(char)('a'+i)))
                    , Enchantment.enchantment(Enchantment.definition(
                                    items.getOrThrow(ItemTags.TRIM_TEMPLATES),
                                    items.getOrThrow(ItemTags.TRIM_TEMPLATES),
                                    1,
                                    1,
                                    Enchantment.dynamicCost(0, 0),
                                    Enchantment.dynamicCost(0, 0),
                                    1,
                                    EquipmentSlotGroup.ANY))
                            .exclusiveWith(enchantments.getOrThrow(create("experience_stamps"))));
            register(context,
                    ResourceKey.create(Registries.ENCHANTMENT,
                            ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID, "galactic/template/t_"+(char)('a'+i)))
                    , Enchantment.enchantment(Enchantment.definition(
                                    items.getOrThrow(ItemTags.TRIM_TEMPLATES),
                                    items.getOrThrow(ItemTags.TRIM_TEMPLATES),
                                    1,
                                    1,
                                    Enchantment.dynamicCost(0, 0),
                                    Enchantment.dynamicCost(0, 0),
                                    1,
                                    EquipmentSlotGroup.ANY))
                            .exclusiveWith(enchantments.getOrThrow(create("experience_templates"))));
        }
    }

    private static TagKey<Enchantment> create(String name) {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,name));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
