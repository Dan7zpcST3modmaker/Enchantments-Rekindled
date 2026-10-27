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

public record VoidEnchantmentsPedestalRecipe(Ingredient inputItem, ItemStack outputItem) implements Recipe<VoidEnchantmentsPedestalRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(VoidEnchantmentsPedestalRecipeInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }
        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(VoidEnchantmentsPedestalRecipeInput input, HolderLookup.Provider registries) {
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
        return ModRecipes.VOID_ENCHANTMENTS_PEDESTAL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<VoidEnchantmentsPedestalRecipe>{
        public static final MapCodec<VoidEnchantmentsPedestalRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(VoidEnchantmentsPedestalRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(VoidEnchantmentsPedestalRecipe::outputItem)
        ).apply(inst, VoidEnchantmentsPedestalRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, VoidEnchantmentsPedestalRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, VoidEnchantmentsPedestalRecipe::inputItem,
                        ItemStack.STREAM_CODEC, VoidEnchantmentsPedestalRecipe::outputItem,
                        VoidEnchantmentsPedestalRecipe::new);

        @Override
        public MapCodec<VoidEnchantmentsPedestalRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VoidEnchantmentsPedestalRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
