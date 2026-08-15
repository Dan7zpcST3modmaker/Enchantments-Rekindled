package net.dan7zpc.enchantments_rekindled.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dan7zpc.enchantments_rekindled.util.EnchantmentHelper;
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

public record EnchantmentsPedestalRecipe(Ingredient inputItem, ItemStack outputItem) implements Recipe<EnchantmentsPedestalRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(EnchantmentsPedestalRecipeInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }
        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(EnchantmentsPedestalRecipeInput input, HolderLookup.Provider registries) {
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
        return ModRecipes.ENCHANTMENTS_PEDESTAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ENCHANTMENTS_PEDESTAL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<EnchantmentsPedestalRecipe>{
        public static final MapCodec<EnchantmentsPedestalRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(EnchantmentsPedestalRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(EnchantmentsPedestalRecipe::outputItem)
        ).apply(inst,EnchantmentsPedestalRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf,EnchantmentsPedestalRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, EnchantmentsPedestalRecipe::inputItem,
                        ItemStack.STREAM_CODEC, EnchantmentsPedestalRecipe::outputItem,
                        EnchantmentsPedestalRecipe::new);

        @Override
        public MapCodec<EnchantmentsPedestalRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EnchantmentsPedestalRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
