package net.dan7zpc.enchantments_rekindled.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record TableOfKnowledgeRecipe(Ingredient inputItem,ItemStack rune1,ItemStack rune2,ItemStack rune3,ItemStack rune4, ItemStack outputItem) implements Recipe<TableOfKnowledgeRecipeInput> {
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        list.add(Ingredient.of(rune1));
        list.add(Ingredient.of(rune2));
        list.add(Ingredient.of(rune3));
        list.add(Ingredient.of(rune4));
        return list;
    }

    @Override
    public boolean matches(TableOfKnowledgeRecipeInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }
        boolean inputMatch = inputItem.test(input.getItem(0));
        if (!inputMatch){
            return false;
        }
        boolean runeMatch = false;

        ItemStack[] runes = {rune1,rune2,rune3,rune4};
        for(int i = 0; i < 4; i++){
            ItemStack testRune1 = runes[(i)%4];
            ItemStack testRune2 = runes[(i+1)%4];
            ItemStack testRune3 = runes[(i+2)%4];
            ItemStack testRune4 = runes[(i+3)%4];

            boolean rune1Match = Ingredient.of(testRune1).test(input.getItem(1)) && sameEnchants(testRune1,input.getItem(1));
            boolean rune2Match = Ingredient.of(testRune2).test(input.getItem(2)) && sameEnchants(testRune2,input.getItem(2));
            boolean rune3Match = Ingredient.of(testRune3).test(input.getItem(3)) && sameEnchants(testRune3,input.getItem(3));
            boolean rune4Match = Ingredient.of(testRune4).test(input.getItem(4)) && sameEnchants(testRune4,input.getItem(4));

            if (rune1Match && rune2Match && rune3Match && rune4Match){
                runeMatch = true;
                break;
            }
        }

        return runeMatch;
    }

    private boolean sameEnchants(ItemStack a, ItemStack b){
        var a_ench = EnchantmentHelper.getEnchantmentsForCrafting(a).entrySet();
        var b_ench = EnchantmentHelper.getEnchantmentsForCrafting(b).entrySet();
        return a_ench.equals(b_ench);
    }

    @Override
    public ItemStack assemble(TableOfKnowledgeRecipeInput input, HolderLookup.Provider registries) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputItem.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.TABLE_OF_KNOWLEDGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.TABLE_OF_KNOWLEDGE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<TableOfKnowledgeRecipe>{
        public static final MapCodec<TableOfKnowledgeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(TableOfKnowledgeRecipe::inputItem),
                ItemStack.CODEC.fieldOf("rune1").forGetter(TableOfKnowledgeRecipe::rune1),
                ItemStack.CODEC.fieldOf("rune2").forGetter(TableOfKnowledgeRecipe::rune2),
                ItemStack.CODEC.fieldOf("rune3").forGetter(TableOfKnowledgeRecipe::rune3),
                ItemStack.CODEC.fieldOf("rune4").forGetter(TableOfKnowledgeRecipe::rune4),
                ItemStack.CODEC.fieldOf("result").forGetter(TableOfKnowledgeRecipe::outputItem)
        ).apply(inst, TableOfKnowledgeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, TableOfKnowledgeRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, TableOfKnowledgeRecipe::inputItem,
                        ItemStack.STREAM_CODEC, TableOfKnowledgeRecipe::rune1,
                        ItemStack.STREAM_CODEC, TableOfKnowledgeRecipe::rune2,
                        ItemStack.STREAM_CODEC, TableOfKnowledgeRecipe::rune3,
                        ItemStack.STREAM_CODEC, TableOfKnowledgeRecipe::rune4,
                        ItemStack.STREAM_CODEC, TableOfKnowledgeRecipe::outputItem,
                        TableOfKnowledgeRecipe::new);

        @Override
        public MapCodec<TableOfKnowledgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TableOfKnowledgeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
