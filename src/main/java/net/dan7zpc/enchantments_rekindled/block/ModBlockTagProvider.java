package net.dan7zpc.enchantments_rekindled.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider {
    public static final TagKey<Block> SANDSTONE_BLOCKS =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c","sandstone/blocks"));
    public static final TagKey<Block> STONE_BLOCKS =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c","stones"));
    public static final TagKey<Block> SLABS =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft","slabs"));
    public static final TagKey<Block> NETHER_STONES =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft","base_stone_nether"));
    public static final TagKey<Block> END_STONES =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c","end_stones"));
}
