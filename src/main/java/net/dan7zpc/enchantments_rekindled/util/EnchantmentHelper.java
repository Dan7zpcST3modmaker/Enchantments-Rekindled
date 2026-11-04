package net.dan7zpc.enchantments_rekindled.util;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class EnchantmentHelper {
    private static Random rand = new Random();
    private static final Map<ResourceLocation,String> TEMPLATE_TO_ID_MAP = new HashMap<>();
    private static final Map<ItemLike,ItemLike> HOLDER_TO_TEMPLATE_MAP = new HashMap<>();
    private static final String[] voidbound_stamps = {
            "alpha", "beta", "gamma", "delta",
            "epsilon", "zeta", "eta", "theta",
            "iota", "kappa", "lambda", "mu",
            "nu", "xi", "omicron", "pi",
            "rho", "sigma", "tau", "upsilon",
            "phi", "chi", "psi", "omega"
    };
    static {
        for (int i = 0; i < 26; i++) {
            ResourceLocation enchantmentTemplateKey = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID, "galactic/template/t_" + (char)('a'+i));

            TEMPLATE_TO_ID_MAP.put(
                    enchantmentTemplateKey,
                    ""+(char)('a'+i)
            );
        }

        for (int i = 0; i < voidbound_stamps.length; i++) {
            ResourceLocation enchantmentTemplateKey = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID, "greek/template/t_"+voidbound_stamps[i]);

            TEMPLATE_TO_ID_MAP.put(
                    enchantmentTemplateKey,
                    voidbound_stamps[i]
            );
        }

        HOLDER_TO_TEMPLATE_MAP.put(ModItems.EXPERIOLITE.get(),ModItems.ETHERIUM_RUNIC_TEMPLATE.get());
    }

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

    public static ItemStack affixVoidboundStamp(ItemStack stack, String id, Level world){
        return applyEnchantment(stack,ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"greek/"+id),1,world);
    }
    public static ItemStack affixRandomVoidboundStamp(ItemStack stack, Level world){
        return affixVoidboundStamp(stack, randomVoidboundStampId(),world);
    }

    private static String randomExperienceStampId() {
        return String.valueOf((char)('a'+ rand.nextInt(26)));
    }

    private static String randomVoidboundStampId() {
        return voidbound_stamps[rand.nextInt(24)];
    }

    public static String getStampNameFromTemplate(ResourceLocation enchKey) {
        return TEMPLATE_TO_ID_MAP.get(enchKey);
    }

    public static boolean isValidTemplateForStack(ItemStack template, ItemStack stack) {
        ItemLike itemLike = HOLDER_TO_TEMPLATE_MAP.getOrDefault(stack.getItem(), ItemStack.EMPTY.getItem());
        //EnchantmentsRekindled.LOGGER.debug(itemLike.asItem().getName(new ItemStack(itemLike)).getString());
        //EnchantmentsRekindled.LOGGER.info("meow");
        return (itemLike == template.getItem());
    }
}
