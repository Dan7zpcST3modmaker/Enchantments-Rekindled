package net.dan7zpc.enchantments_rekindled.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record VoidEnchantmentsPedestalRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getItem(int i){
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
