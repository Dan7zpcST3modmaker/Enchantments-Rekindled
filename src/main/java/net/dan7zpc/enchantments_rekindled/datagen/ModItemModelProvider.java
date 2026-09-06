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
        basicItem(ModItems.ARCHETHER.get());
        basicItem(ModItems.RAW_PROETHER.get());
        basicItem(ModItems.PROETHER.get());
        basicItem(ModItems.ETHERIUM.get());
        basicItem(ModItems.ETHERIUM_RUNIC_TEMPLATE.get());
        basicItem(ModItems.VOIDPOWDER.get());
        basicItem(ModItems.METAL_SCRAP.get());
        basicItem(ModItems.BROKER_SHARD.get());
        basicItem(ModItems.NOCTETHER.get());
        basicItem(ModItems.ABYSSAL_JAM.get());
        basicItem(ModItems.VOIDSTEEL_INGOT.get());
        basicItem(ModItems.VOIDSTEEL_NUGGET.get());
    }
}
