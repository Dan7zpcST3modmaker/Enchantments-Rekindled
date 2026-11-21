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

public record NetherBurnerRecipe(ItemStack inp1, ItemStack inp2,ItemStack outputItem) implements Recipe<NetherBurnerRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.of(inp1));
        list.add(Ingredient.of(inp2));
        return list;
    }

    @Override
    public boolean matches(NetherBurnerRecipeInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }
        boolean linear_match =inp1.is(input.getItem(0).getItem()) && inp2.is(input.getItem(1).getItem());
        boolean mirrored_match =inp1.is(input.getItem(1).getItem()) && inp2.is(input.getItem(2).getItem());
        return linear_match||mirrored_match;
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
                ItemStack.CODEC.fieldOf("input_1").forGetter(NetherBurnerRecipe::inp1),
                ItemStack.CODEC.fieldOf("input_2").forGetter(NetherBurnerRecipe::inp2),
                ItemStack.CODEC.fieldOf("result").forGetter(NetherBurnerRecipe::outputItem)
        ).apply(inst, NetherBurnerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, NetherBurnerRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ItemStack.STREAM_CODEC, NetherBurnerRecipe::inp1,
                        ItemStack.STREAM_CODEC, NetherBurnerRecipe::inp2,
                        ItemStack.STREAM_CODEC, NetherBurnerRecipe::outputItem,
                        NetherBurnerRecipe::new);

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
