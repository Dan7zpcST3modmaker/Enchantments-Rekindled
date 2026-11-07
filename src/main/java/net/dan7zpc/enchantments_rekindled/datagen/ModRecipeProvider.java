package net.dan7zpc.enchantments_rekindled.datagen;

import net.dan7zpc.enchantments_rekindled.block.ModBlocks;
import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.util.EnchantmentHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        furnace_recipe(recipeOutput, ModItems.NYCRIT.get(),ModItems.EXPERIENCE_CRYSTAL.get(),
                new FurnaceRecipeType[]{FurnaceRecipeType.Default,FurnaceRecipeType.Blasting},0f);

        furnace_recipe(recipeOutput, ModItems.METAL_SCRAP.get(),Items.IRON_NUGGET,
                new FurnaceRecipeType[]{FurnaceRecipeType.Default,FurnaceRecipeType.Blasting},0f);

        furnace_recipe(recipeOutput, ModItems.RAW_PROETHER.get(),ModItems.PROETHER.get(),
                new FurnaceRecipeType[]{FurnaceRecipeType.Blasting},0f,400);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.EXPERIOLITE.get())
                .pattern("D#D")
                .pattern("###")
                .pattern("D#D")
                .define('D', Items.DIAMOND)
                .define('#', ModItems.EXPERIENCE_CRYSTAL.get())
                .unlockedBy(getHasName(ModItems.EXPERIENCE_CRYSTAL.get()),has(ModItems.EXPERIENCE_CRYSTAL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.VOIDOLITE.get())
                .pattern("D#D")
                .pattern("###")
                .pattern("D#D")
                .define('D', ModItems.NOCTETHER.get())
                .define('#', ModItems.VOIDSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.NOCTETHER.get()),has(ModItems.NOCTETHER.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.NYCRIT_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.NYCRIT.get())
                .unlockedBy(getHasName(ModItems.NYCRIT.get()),has(ModItems.NYCRIT.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.NYCRIT.get(),9)
                .requires(ModBlocks.NYCRIT_BLOCK)
                .unlockedBy(getHasName(ModBlocks.NYCRIT_BLOCK.get()),has(ModBlocks.NYCRIT_BLOCK.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.VOIDSTEEL_NUGGET.get(),9)
                .requires(ModItems.VOIDSTEEL_INGOT)
                .unlockedBy(getHasName(ModBlocks.NYCRIT_BLOCK.get()),has(ModBlocks.NYCRIT_BLOCK.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.VOIDSTEEL_INGOT.get())
                .requires(ModItems.VOIDSTEEL_NUGGET,9)
                .unlockedBy(getHasName(ModBlocks.NYCRIT_BLOCK.get()),has(ModBlocks.NYCRIT_BLOCK.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.ARCHETHER.get())
                .requires(Items.IRON_INGOT,2)
                .requires(Items.GOLD_INGOT,2)
                .requires(Items.LAPIS_LAZULI,4)
                .unlockedBy(getHasName(ModBlocks.NYCRIT_BLOCK.get()),has(ModBlocks.NYCRIT_BLOCK.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.EXPERIENCE_PRISM.get())
                .pattern(" # ")
                .pattern("#F#")
                .pattern(" # ")
                .define('#', ModItems.EXPERIENCE_CRYSTAL)
                .define('F', Items.FLINT)
                .unlockedBy(getHasName(ModItems.EXPERIENCE_CRYSTAL.get()),has(ModItems.EXPERIENCE_CRYSTAL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.ENCHANTMENTS_PEDESTAL.get())
                .pattern("III")
                .pattern("POP")
                .pattern("OOO")
                .define('I', Items.IRON_INGOT)
                .define('P', ModItems.EXPERIENCE_PRISM)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(getHasName(ModItems.EXPERIENCE_PRISM.get()),has(ModItems.EXPERIENCE_PRISM.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.TABLE_OF_KNOWLEDGE.get())
                .pattern("III")
                .pattern("POP")
                .pattern("OOO")
                .define('I', ModItems.ETHERIUM)
                .define('P', ModItems.EXPERIENCE_PRISM)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(getHasName(ModItems.ETHERIUM.get()),has(ModItems.ETHERIUM.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.SOAP_STONE.get())
                .pattern("CGC")
                .pattern("GCG")
                .pattern("CGC")
                .define('C', Items.CLAY)
                .define('G', Items.GUNPOWDER)
                .unlockedBy(getHasName(ModBlocks.ENCHANTMENTS_PEDESTAL.get()),has(ModBlocks.ENCHANTMENTS_PEDESTAL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.ENCHANTMENTS_BRICKS.get())
                .pattern("EE")
                .pattern("EE")
                .define('E', ModBlocks.ENCHANTMENTS_STONE)
                .unlockedBy(getHasName(ModBlocks.ENCHANTMENTS_STONE.get()),has(ModBlocks.ENCHANTMENTS_STONE.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.RITUAL_BLOCK.get())
                .pattern("PEP")
                .pattern("EGE")
                .pattern("PEP")
                .define('E', ModItems.ETHERIUM)
                .define('P', ModItems.EXPERIENCE_PRISM)
                .define('G', Items.GOLD_BLOCK)
                .unlockedBy(getHasName(Items.GOLD_BLOCK),has(Items.GOLD_BLOCK))
                .save(recipeOutput);

        //stonecutterResultFromBase(recipeOutput,RecipeCategory.BUILDING_BLOCKS,ModBlocks.RITUAL_BLOCK.get(),Items.GOLD_BLOCK);
    }

    protected void furnace_recipe(RecipeOutput recipeOutput, ItemLike in, ItemLike out, FurnaceRecipeType[] furnaceRecipeTypes){
        furnace_recipe(recipeOutput,in,out,furnaceRecipeTypes,0.25f,200);
    }

    protected void furnace_recipe(RecipeOutput recipeOutput, ItemLike in, ItemLike out, FurnaceRecipeType[] furnaceRecipeTypes,float experience){
        furnace_recipe(recipeOutput,in,out,furnaceRecipeTypes,experience,200);
    }

    protected void furnace_recipe(RecipeOutput recipeOutput, ItemLike in, ItemLike out, FurnaceRecipeType[] furnaceRecipeTypes,int cooking_time){
        furnace_recipe(recipeOutput,in,out,furnaceRecipeTypes,0.25f,cooking_time);
    }

    protected void furnace_recipe(RecipeOutput recipeOutput, ItemLike in, ItemLike out, FurnaceRecipeType[] furnaceRecipeTypes,float experience,int cooking_time){
        List<ItemLike> input = List.of(in);
        Stream<FurnaceRecipeType> stream = Arrays.stream(furnaceRecipeTypes);
        if(stream.anyMatch(a -> a == FurnaceRecipeType.Default)){
            oreSmelting(recipeOutput, input, RecipeCategory.MISC,out,experience, cooking_time, "group");
        }
        stream = Arrays.stream(furnaceRecipeTypes);
        if(stream.anyMatch(a -> a == FurnaceRecipeType.Blasting)){
            oreBlasting(recipeOutput, input, RecipeCategory.MISC,out,experience, cooking_time/2, "group");
        }
    }
}
