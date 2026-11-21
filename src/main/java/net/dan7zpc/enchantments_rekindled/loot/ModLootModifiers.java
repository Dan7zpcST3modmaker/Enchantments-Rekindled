package net.dan7zpc.enchantments_rekindled.loot;

import com.mojang.serialization.MapCodec;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EnchantmentsRekindled.MOD_ID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> REPLACE_ITEM =
            LOOT_MODIFIERS.register("replace_item",() -> LootItemReplacer.CODEC);

    public static void register(IEventBus eventBus){
        LOOT_MODIFIERS.register(eventBus);
    }
}
