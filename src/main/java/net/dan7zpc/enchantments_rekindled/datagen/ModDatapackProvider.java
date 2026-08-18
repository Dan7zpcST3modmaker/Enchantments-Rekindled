package net.dan7zpc.enchantments_rekindled.datagen;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.enchantment.ModEnchantments;
import net.dan7zpc.enchantments_rekindled.worldgen.ModBiomeModifiers;
import net.dan7zpc.enchantments_rekindled.worldgen.ModConfiguredFeatures;
import net.dan7zpc.enchantments_rekindled.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS , ModBiomeModifiers::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
            ;

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(EnchantmentsRekindled.MOD_ID));
    }
}
