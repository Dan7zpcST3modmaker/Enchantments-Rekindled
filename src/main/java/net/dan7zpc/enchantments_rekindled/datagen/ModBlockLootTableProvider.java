package net.dan7zpc.enchantments_rekindled.datagen;

import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.NYCRIT_BLOCK.get());
        dropSelf(ModBlocks.ENCHANTMENTS_STONE.get());
        dropSelf(ModBlocks.ENCHANTMENTS_PEDESTAL.get());
        dropSelf(ModBlocks.ENCHANTMENTS_BRICKS.get());
        dropSelf(ModBlocks.SOAP_STONE.get());
        dropSelf(ModBlocks.TABLE_OF_KNOWLEDGE.get());
        dropSelf(ModBlocks.RITUAL_BLOCK.get());
        dropSelf(ModBlocks.BROKER_STONE.get());

        dropOre(ModBlocks.NYCRIT_ORE.get(),ModItems.NYCRIT.get());
        dropOre(ModBlocks.DEEPSLATE_NYCRIT_ORE.get(),ModItems.NYCRIT.get());
    }

    protected void dropOre(Block block, Item item){
        add(block, block1 -> createOreDrop(block,item));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
