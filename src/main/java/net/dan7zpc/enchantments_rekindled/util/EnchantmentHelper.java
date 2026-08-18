package net.dan7zpc.enchantments_rekindled.util;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class EnchantmentHelper {
    private static Random rand = new Random();

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
    public static ItemStack affixExperienceStamp(ItemStack stack, String id, Level world){
        return applyEnchantment(stack,ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"galactic/"+id),1,world);
    }
    public static ItemStack affixRandomExperienceStamp(ItemStack stack, Level world){
        return affixExperienceStamp(stack, randomExperienceStampId(),world);
    }

    private static String randomExperienceStampId() {
        return String.valueOf((char)('a'+ rand.nextInt(26)));
    }
}
