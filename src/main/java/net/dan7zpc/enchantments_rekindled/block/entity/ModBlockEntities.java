package net.dan7zpc.enchantments_rekindled.block.entity;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, EnchantmentsRekindled.MOD_ID);

    public static final Supplier<BlockEntityType<EnchantmentsPedestalEntity>> ENCHANTMENTS_PEDESTAL_BE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("enchantments_pedestal_be",() -> BlockEntityType.Builder.of(
                    EnchantmentsPedestalEntity::new, ModBlocks.ENCHANTMENTS_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<TableOfKnowledgeEntity>> TABLE_OF_KNOWLEDGE_BE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("table_of_knowledge_be",() -> BlockEntityType.Builder.of(
                    TableOfKnowledgeEntity::new, ModBlocks.TABLE_OF_KNOWLEDGE.get()).build(null));

    public static final Supplier<BlockEntityType<RitualAltarEntity>> RITUAL_ALTAR_BE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("ritual_altar_be",() -> BlockEntityType.Builder.of(
                    RitualAltarEntity::new, ModBlocks.RITUAL_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
