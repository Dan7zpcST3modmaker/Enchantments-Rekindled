package net.dan7zpc.enchantments_rekindled.block;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.custom.Pedestal;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(EnchantmentsRekindled.MOD_ID);

    public static final DeferredBlock<Block> NYCRIT_BLOCK = registerBlock("nycrit_block",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(4)
                    .lightLevel(value -> 15)
                    .sound(SoundType.AMETHYST)
            ));
    public static final DeferredBlock<Block> NYCRIT_ORE = registerBlock("nycrit_ore",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(4)
                    .sound(SoundType.STONE)
            ));
    public static final DeferredBlock<Block> ENCHANTMENTS_STONE = registerBlock("enchantments_stone",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(4)
                    .sound(SoundType.STONE)
            ));
    public static final DeferredBlock<Block> ENCHANTMENTS_PEDESTAL = registerBlock("enchantments_pedestal",
            ()-> new Pedestal(BlockBehaviour.Properties.of()
                    .strength(4)
                    .explosionResistance(5f)
                    .lightLevel(val -> 8)
                    .noOcclusion()
                    .sound(SoundType.STONE)
            ));
    public static final DeferredBlock<Block> DEEPSLATE_NYCRIT_ORE = registerBlock("deepslate_nycrit_ore",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .strength(4)
                    .sound(SoundType.DEEPSLATE)
            ));

    public static final DeferredBlock<Block> ABYSSAL_BARRIER = registerBlock("abyssal_barrier",
            ()-> new Block(BlockBehaviour.Properties.of()
                    .noLootTable()
                    .noTerrainParticles()
                    .speedFactor(0.3f)
                    .strength(-1f)
                    .pushReaction(PushReaction.BLOCK)
                    .explosionResistance(9999)
                    .sound(SoundType.MUD)
            ));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
