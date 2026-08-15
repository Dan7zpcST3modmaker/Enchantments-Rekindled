package net.dan7zpc.enchantments_rekindled.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class Reward {
    private ItemStack[] items;
    private int experience;
    public Reward(ItemStack[] _items, int _experience){
        this.items = _items;
        this.experience = _experience;
    }
    public Reward(ItemStack _item, int _experience){
        this.items = new ItemStack[]{_item};
        this.experience = _experience;
    }
    public Reward(Item _item, int _experience){
        this.items = new ItemStack[]{new ItemStack(_item)};
        this.experience = _experience;
    }
    public int getExperience(){
        return this.experience;
    }
    public ItemStack[] getItems() {
        return this.items;
    }
}
