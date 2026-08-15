package net.dan7zpc.enchantments_rekindled.item;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EnchantmentsRekindled.MOD_ID);


    public static final Supplier<CreativeModeTab> ER_MOD_TAB = CREATIVE_MODE_TAB.register("mod_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.EXPERIOLITE.get()))
                    .title(Component.translatable("creativetab."+EnchantmentsRekindled.MOD_ID+".mod_items_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.NYCRIT);
                        output.accept(ModItems.EXPERIENCE_CRYSTAL);
                        output.accept(ModItems.EXPERIOLITE);
                        output.accept(ModItems.EXPERIENCE_PRISM);
                        output.accept(ModBlocks.NYCRIT_BLOCK);
                        output.accept(ModBlocks.NYCRIT_ORE);
                        output.accept(ModBlocks.DEEPSLATE_NYCRIT_ORE);
                        output.accept(ModBlocks.ENCHANTMENTS_PEDESTAL);
                        output.accept(ModBlocks.ENCHANTMENTS_STONE);
                        output.accept(ModBlocks.ABYSSAL_BARRIER);
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
