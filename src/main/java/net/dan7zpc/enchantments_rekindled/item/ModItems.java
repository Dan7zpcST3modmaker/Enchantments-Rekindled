package net.dan7zpc.enchantments_rekindled.item;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.item.custom.Reward;
import net.dan7zpc.enchantments_rekindled.item.custom.item.CushableByHandItem;
import net.dan7zpc.enchantments_rekindled.item.custom.item.QuickTooltipItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EnchantmentsRekindled.MOD_ID);

    public static final DeferredItem<Item> NYCRIT = ITEMS.register("nycrit",
            ()-> new QuickTooltipItem(new Item.Properties(),"","nycrit_shift","nycrit_control"));

    public static final DeferredItem<Item> EXPERIOLITE = ITEMS.register("experiolite",
            ()-> new QuickTooltipItem(new Item.Properties(),"experiolite","experiolite_shift",""));

    public static final DeferredItem<Item> EXPERIENCE_CRYSTAL = ITEMS.register("experience_crystal",
            ()-> new CushableByHandItem(new Item.Properties(),new Reward(new ItemStack[]{}, 3)));

    public static final DeferredItem<Item> EXPERIENCE_PRISM = ITEMS.register("experience_prism",
            ()-> new Item(new Item.Properties()));


    //experiolite
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
