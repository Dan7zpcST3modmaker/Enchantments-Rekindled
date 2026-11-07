package net.dan7zpc.enchantments_rekindled.item;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.item.custom.FuelItem;
import net.dan7zpc.enchantments_rekindled.item.custom.Reward;
import net.dan7zpc.enchantments_rekindled.item.custom.item.*;
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

    public static final DeferredItem<Item> ARCHETHER = ITEMS.register("archether",
            ()-> new CraftableByHandItem(new Item.Properties(),25));

    public static final DeferredItem<Item> RAW_PROETHER = ITEMS.register("raw_proether",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PROETHER = ITEMS.register("proether",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ETHERIUM = ITEMS.register("etherium",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ETHERIUM_RUNIC_TEMPLATE = ITEMS.register("etherium_runic_template",
            ()-> new QuickTooltipItem(new Item.Properties(),"etherium_runic_template","etherium_runic_template_shift",""));

    public static final DeferredItem<Item> VOIDPOWDER = ITEMS.register("voidpowder",
            ()-> new CushableByHandItem(new Item.Properties(),new Reward(new ItemStack[]{}, 0)));

    public static final DeferredItem<Item> METAL_SCRAP = ITEMS.register("metal_scrap",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BROKER_SHARD = ITEMS.register("broker_shard",
            ()-> new FuelItem(new Item.Properties(),3200));

    public static final DeferredItem<Item> NOCTETHER = ITEMS.register("noctether",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ABYSSAL_JAM = ITEMS.register("abyssal_jam",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOID_TRINKET = ITEMS.register("void_trinket",
            ()-> new VoidTrinket(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> VOIDSTEEL_INGOT = ITEMS.register("voidsteel_ingot",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOIDSTEEL_NUGGET = ITEMS.register("voidsteel_nugget",
            ()-> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOIDOLITE = ITEMS.register("voidolite",
            ()-> new QuickTooltipItem(new Item.Properties(),"voidolite","voidolite_shift",""));
    public static final DeferredItem<Item> ABYSSAL_ESSENCE = ITEMS.register("abyssal_essence",
            ()-> new QuickTooltipItem(new Item.Properties(),"abyssal_essence","",""));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
