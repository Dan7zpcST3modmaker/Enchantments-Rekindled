package net.dan7zpc.enchantments_rekindled.abyss;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.resources.ResourceLocation;

public class ModVoidEvents {
    public static final VoidEvent broker = new VoidEvent("broker_spawn", new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"broker")
    });
}
