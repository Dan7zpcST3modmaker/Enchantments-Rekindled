package net.dan7zpc.enchantments_rekindled.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record NetherBurnerRecipeInput(List<Ingredient> ingredients) implements RecipeInput {
    @Override
    public ItemStack getItem(int i){
        return ingredients.get(i).getItems()[0];
    }

    @Override
    public int size() {
        return ingredients.size();
    }
}
