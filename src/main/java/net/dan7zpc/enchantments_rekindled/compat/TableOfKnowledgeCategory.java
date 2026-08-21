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
import net.dan7zpc.enchantments_rekindled.recipe.TableOfKnowledgeRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TableOfKnowledgeCategory implements IRecipeCategory<TableOfKnowledgeRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"table_of_knowledge");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,
            "textures/gui/table_of_knowledge/table_of_knowledge_jei_gui.png");

    public static final RecipeType<TableOfKnowledgeRecipe> TABLE_OF_KNOWLEDGE_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, TableOfKnowledgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public TableOfKnowledgeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,96,128);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.TABLE_OF_KNOWLEDGE));
    }

    @Override
    public RecipeType<TableOfKnowledgeRecipe> getRecipeType() {
        return TABLE_OF_KNOWLEDGE_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe."+EnchantmentsRekindled.MOD_ID+".enchantments_pedestal");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TableOfKnowledgeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 40,82).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.INPUT, 40,55).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 67,82).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 40,109).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 13,82).addIngredients(recipe.getIngredients().get(4));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 40,5).addItemStack(recipe.getResultItem(null));

        //builder.addSlot(RecipeIngredientRole.CATALYST, -16,-16).addItemLike(ModBlocks.ENCHANTMENTS_PEDESTAL);
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }
}
