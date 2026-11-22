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
import net.dan7zpc.enchantments_rekindled.recipe.EnchantmentsPedestalRecipe;
import net.dan7zpc.enchantments_rekindled.recipe.NetherBurnerRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherBurnerCategory implements IRecipeCategory<NetherBurnerRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,"nether_burner");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID,
            "textures/gui/nether_burner/nether_burner_jei_gui.png");

    public static final RecipeType<NetherBurnerRecipe> NETHER_BURNER_RECIPE_TYPE =
            new RecipeType<>(UID, NetherBurnerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public NetherBurnerCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,96,160);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.NETHER_BURNER_BLOCK));
    }

    @Override
    public RecipeType<NetherBurnerRecipe> getRecipeType() {
        return NETHER_BURNER_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe."+EnchantmentsRekindled.MOD_ID+".nether_burner");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NetherBurnerRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int amount = ingredients.size();
        for(int i =0; i < amount; i++){
            Ingredient ingredient = ingredients.get(i);
            int offset = Math.min(76/amount,16);
            builder.addSlot(RecipeIngredientRole.INPUT, 40,66-(offset*i)).addIngredients(ingredient);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 40,138).addItemStack(recipe.getResultItem(null));

        //builder.addSlot(RecipeIngredientRole.CATALYST, -16,-16).addItemLike(ModBlocks.ENCHANTMENTS_PEDESTAL);
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }
}
