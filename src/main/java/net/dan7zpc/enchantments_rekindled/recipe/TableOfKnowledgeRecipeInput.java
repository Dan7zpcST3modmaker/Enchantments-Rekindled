package net.dan7zpc.enchantments_rekindled.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;

public record TableOfKnowledgeRecipeInput(ItemStack input, ItemStack rune1, ItemStack rune2, ItemStack rune3, ItemStack rune4) implements RecipeInput {
    @Override
    public ItemStack getItem(int i)
    {
        return switch (i){
            case 0 -> input;
            case 1 -> rune1;
            case 2 -> rune2;
            case 3 -> rune3;
            case 4 -> rune4;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 5;
    }
}
