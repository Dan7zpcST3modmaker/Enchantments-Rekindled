package net.dan7zpc.enchantments_rekindled.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipe;
import net.dan7zpc.enchantments_rekindled.recipe.ItemCrushRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemCrushCategory implements IRecipeCategory<ItemCrushRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"item_crush");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,
            "textures/gui/item_crush/item_crush_jei_gui.png");

    public static final RecipeType<ItemCrushRecipe> ITEM_CRUSH_RECIPE_TYPE =
            new RecipeType<>(UID, ItemCrushRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ItemCrushCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,38,78);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.EXPERIENCE_CRYSTAL.get()));
    }

    @Override
    public RecipeType<ItemCrushRecipe> getRecipeType() {
        return ITEM_CRUSH_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe."+EnchantmentsRekindled.MOD_ID+".item_crush");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemCrushRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 11,51).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 11,5).addItemStack(recipe.getResultItem(null));

        //builder.addSlot(RecipeIngredientRole.CATALYST, -16,-16).addItemLike(ModBlocks.ENCHANTMENTS_PEDESTAL);
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }
}
