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
import net.minecraft.world.level.Level;

public record ItemCrushRecipe(Ingredient inputItem, ItemStack outputItem) implements Recipe<ItemCrushRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(ItemCrushRecipeInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }
        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(ItemCrushRecipeInput input, HolderLookup.Provider registries) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputItem;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ITEM_CRUSH_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ITEM_CRUSH_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ItemCrushRecipe>{
        public static final MapCodec<ItemCrushRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ItemCrushRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(ItemCrushRecipe::outputItem)
        ).apply(inst, ItemCrushRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ItemCrushRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ItemCrushRecipe::inputItem,
                        ItemStack.STREAM_CODEC, ItemCrushRecipe::outputItem,
                        ItemCrushRecipe::new);

        @Override
        public MapCodec<ItemCrushRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ItemCrushRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
