package net.dan7zpc.enchantments_rekindled.datagen;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EnchantmentsRekindled.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.NYCRIT.get());
        basicItem(ModItems.EXPERIENCE_CRYSTAL.get());
        basicItem(ModItems.EXPERIOLITE.get());
        basicItem(ModItems.EXPERIENCE_PRISM.get());
    }
}
