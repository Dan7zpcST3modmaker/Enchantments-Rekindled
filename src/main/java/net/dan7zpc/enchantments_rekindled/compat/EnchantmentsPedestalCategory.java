package net.dan7zpc.enchantments_rekindled.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EnchantmentsPedestalCategory implements IRecipeCategory<EnchantmentsPedestalRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"enchantments_pedestal");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,
            "textures/gui/enchantments_pedestal/enchantments_pedestal_jei_gui.png");

    public static final RecipeType<EnchantmentsPedestalRecipe> ENCHANTMENTS_PEDESTAL_RECIPE_TYPE =
            new RecipeType<>(UID, EnchantmentsPedestalRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public EnchantmentsPedestalCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ENCHANTMENTS_PEDESTAL));
    }

    @Override
    public RecipeType<EnchantmentsPedestalRecipe> getRecipeType() {
        return ENCHANTMENTS_PEDESTAL_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.enchantments_rekindled.enchantments_pedestal");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EnchantmentsPedestalRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54,34).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 104,34).addItemStack(recipe.getResultItem(null));

        builder.addSlot(RecipeIngredientRole.CATALYST, 0,0).addItemLike(ModBlocks.ENCHANTMENTS_PEDESTAL);
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }
}
