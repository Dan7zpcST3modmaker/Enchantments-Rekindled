package net.dan7zpc.enchantments_rekindled.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record NetherBurnerRecipe(List<Ingredient> ingredients, ItemStack outputItem) implements Recipe<NetherBurnerRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(ingredients);
        return list;
    }

    @Override
    public boolean matches(NetherBurnerRecipeInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }
        if(input.size()!=ingredients.size())return false;
        for(int i = 0; i < ingredients.size(); i++){
            Ingredient blockIngredient = ingredients.get(i);
            boolean match = blockIngredient.test(input.getItem(i));
            if(!match)return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(NetherBurnerRecipeInput input, HolderLookup.Provider registries) {
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
        return ModRecipes.NETHER_BURNER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.NETHER_BURNER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<NetherBurnerRecipe>{
        public static final MapCodec<NetherBurnerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(NetherBurnerRecipe::getIngredients),
                ItemStack.CODEC.fieldOf("result").forGetter(NetherBurnerRecipe::outputItem)
        ).apply(inst, NetherBurnerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, NetherBurnerRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                        NetherBurnerRecipe::getIngredients,

                        ItemStack.STREAM_CODEC,
                        NetherBurnerRecipe::outputItem,

                        NetherBurnerRecipe::new
                );

        @Override
        public MapCodec<NetherBurnerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, NetherBurnerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
