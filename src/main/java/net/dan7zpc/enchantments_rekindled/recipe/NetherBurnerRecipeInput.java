package net.dan7zpc.enchantments_rekindled.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record NetherBurnerRecipeInput(ItemStack input_1, ItemStack input_2) implements RecipeInput {
    @Override
    public ItemStack getItem(int i){
        return switch (i){
            case 0 -> input_1;
            case 1 -> input_2;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 2;
    }
}
