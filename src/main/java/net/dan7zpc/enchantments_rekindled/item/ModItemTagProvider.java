package net.dan7zpc.enchantments_rekindled.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTagProvider {
    public static final TagKey<Item> INGOTS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","ingots"));
    public static final TagKey<Item> GEMS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","gems"));
    public static final TagKey<Item> STONES =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","stones"));
    public static final TagKey<Item> ORES =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","ores"));
    public static final TagKey<Item> RAW_MATERIALS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","raw_materials"));
    public static final TagKey<Item> NUGGETS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","nuggets"));
    public static final TagKey<Item> LOGS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","logs"));
    public static final TagKey<Item> LEATHERS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","leathers"));
    public static final TagKey<Item> SEEDS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","seeds"));
    public static final TagKey<Item> FOODS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","foods"));
    public static final TagKey<Item> CROPS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","crops"));
    public static final TagKey<Item> STORAGE_BLOCKS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","storage_blocks"));
    public static final TagKey<Item> ABYSS_BLACKLIST =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("enchantments_rekindled","abyss_blacklist"));
}
