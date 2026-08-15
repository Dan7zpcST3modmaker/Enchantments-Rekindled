package net.dan7zpc.enchantments_rekindled.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;


public class EnchantmentHelper {
    public static ItemStack applyEnchantment(ItemStack stack, ResourceLocation id, int level, Level world){
        ItemStack result = stack.copy();

        var registry = world.registryAccess().registryOrThrow(Registries.ENCHANTMENT);

        var enchantmentHolder = registry.getHolder(id).orElse(null);
        if(enchantmentHolder == null){return result;}

        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(
                result.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
        );

        enchantments.set(enchantmentHolder, level);

        result.set(DataComponents.ENCHANTMENTS,enchantments.toImmutable());

        return result;
    }
    public static ItemStack applyEnchantment(ItemStack stack, String id, int level, Level world){
        return applyEnchantment(stack,ResourceLocation.withDefaultNamespace(id),level,world);
    }
}
