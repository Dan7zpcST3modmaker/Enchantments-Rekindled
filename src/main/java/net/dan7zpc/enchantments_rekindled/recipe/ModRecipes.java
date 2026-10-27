package net.dan7zpc.enchantments_rekindled.recipe;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, EnchantmentsRekindled.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, EnchantmentsRekindled.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<EnchantmentsPedestalRecipe>> ENCHANTMENTS_PEDESTAL_SERIALIZER =
            SERIALIZERS.register("enchantments_pedestal",EnchantmentsPedestalRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>,RecipeType<EnchantmentsPedestalRecipe>> ENCHANTMENTS_PEDESTAL_TYPE =
            TYPES.register("enchantments_pedestal", () -> new RecipeType<EnchantmentsPedestalRecipe>() {
                @Override
                public String toString() {
                    return "enchantments_pedestal";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<VoidEnchantmentsPedestalRecipe>> VOID_ENCHANTMENTS_PEDESTAL_SERIALIZER =
            SERIALIZERS.register("void_enchantments_pedestal",VoidEnchantmentsPedestalRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>,RecipeType<VoidEnchantmentsPedestalRecipe>> VOID_ENCHANTMENTS_PEDESTAL_TYPE =
            TYPES.register("void_enchantments_pedestal", () -> new RecipeType<VoidEnchantmentsPedestalRecipe>() {
                @Override
                public String toString() {
                    return "void_enchantments_pedestal";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<ItemCrushRecipe>> ITEM_CRUSH_SERIALIZER =
            SERIALIZERS.register("item_crush",ItemCrushRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>,RecipeType<ItemCrushRecipe>> ITEM_CRUSH_TYPE =
            TYPES.register("item_crush", () -> new RecipeType<ItemCrushRecipe>() {
                @Override
                public String toString() {
                    return "item_crush";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<TableOfKnowledgeRecipe>> TABLE_OF_KNOWLEDGE_SERIALIZER =
            SERIALIZERS.register("table_of_knowledge",TableOfKnowledgeRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>,RecipeType<TableOfKnowledgeRecipe>> TABLE_OF_KNOWLEDGE_TYPE =
            TYPES.register("table_of_knowledge", () -> new RecipeType<TableOfKnowledgeRecipe>() {
                @Override
                public String toString() {
                    return "table_of_knowledge";
                }
            });


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
