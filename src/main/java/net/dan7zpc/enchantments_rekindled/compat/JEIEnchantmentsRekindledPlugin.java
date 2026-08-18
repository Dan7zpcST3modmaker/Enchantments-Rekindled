package net.dan7zpc.enchantments_rekindled.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipe;
import net.dan7zpc.enchantments_rekindled.recipe.ItemCrushRecipe;
import net.dan7zpc.enchantments_rekindled.recipe.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIEnchantmentsRekindledPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new EnchantmentsPedestalCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ItemCrushCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<EnchantmentsPedestalRecipe> enchantmentsPedestalRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.ENCHANTMENTS_PEDESTAL_TYPE.get()).stream().map(RecipeHolder::value).toList();

        registration.addRecipes(EnchantmentsPedestalCategory.ENCHANTMENTS_PEDESTAL_RECIPE_TYPE, enchantmentsPedestalRecipes);

        List<ItemCrushRecipe> itemCrushRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.ITEM_CRUSH_TYPE.get()).stream().map(RecipeHolder::value).toList();

        registration.addRecipes(ItemCrushCategory.ITEM_CRUSH_RECIPE_TYPE, itemCrushRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(EnchantmentsPedestalCategory.ENCHANTMENTS_PEDESTAL_RECIPE_TYPE, ModBlocks.ENCHANTMENTS_PEDESTAL);
    }
}
