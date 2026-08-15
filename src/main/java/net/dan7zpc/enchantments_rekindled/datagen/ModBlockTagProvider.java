package net.dan7zpc.enchantments_rekindled.datagen;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EnchantmentsRekindled.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.NYCRIT_BLOCK.get())
                .add(ModBlocks.NYCRIT_ORE.get())
                .add(ModBlocks.DEEPSLATE_NYCRIT_ORE.get())
                .add(ModBlocks.ENCHANTMENTS_STONE.get())
                .add(ModBlocks.ENCHANTMENTS_PEDESTAL.get())
        ;
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.NYCRIT_BLOCK.get())
        ;
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.NYCRIT_ORE.get())
                .add(ModBlocks.DEEPSLATE_NYCRIT_ORE.get())
        ;
    }
}
