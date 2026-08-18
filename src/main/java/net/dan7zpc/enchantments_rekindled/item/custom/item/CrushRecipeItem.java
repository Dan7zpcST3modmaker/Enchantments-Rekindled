package net.dan7zpc.enchantments_rekindled.item.custom.item;

import net.dan7zpc.enchantments_rekindled.item.ModItems;
import net.dan7zpc.enchantments_rekindled.item.custom.ICrushable;
import net.dan7zpc.enchantments_rekindled.item.custom.Reward;
import net.dan7zpc.enchantments_rekindled.recipe.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class CrushRecipeItem extends CustomModItem implements ICrushable {
    protected Reward reward;

    public CrushRecipeItem(Properties properties,int exp_points_cost) {
        super(properties);
        reward = new Reward(new ItemStack[]{},exp_points_cost);
    }

    private boolean hasRecipe(Level level) {
        ItemStack item = new ItemStack(this);
        Optional<RecipeHolder<ItemCrushRecipe>> recipe = getCurrentRecipe(level);
        if(recipe.isEmpty()){
            return false;
        }
        ItemStack output = recipe.get().value().outputItem();
        return true;
    }



    private Optional<RecipeHolder<ItemCrushRecipe>> getCurrentRecipe(Level level) {
        return  level.getRecipeManager().
                getRecipeFor(ModRecipes.ITEM_CRUSH_TYPE.get(), new ItemCrushRecipeInput(new ItemStack(this)),level);
    }

    private ItemStack crushingResult(Level level){
        Optional<RecipeHolder<ItemCrushRecipe>> recipe = getCurrentRecipe(level);
        ItemStack result = recipe.get().value().outputItem();
        return result;
    }


    @Override
    public void crush(Level level, Player player, InteractionHand usedHand) {
        if(hasRecipe(level) && can_crush(level,player,usedHand)){
            reward = new Reward(
                    new ItemStack[]{crushingResult(level)},
                    reward.getExperience()
            );
            removeItem(player.getItemInHand(usedHand));
            level.playSound(player, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS,0.4f,1f);
            giveTheRewardTo(player);
        }
    }

    private void giveTheRewardTo(Player player) {
        ItemStack[] pool = reward.getItems();
        int exp = reward.getExperience();
        if(pool.length == 0){return;}
        for (int i = 0; i < pool.length; i++) {
            player.addItem(pool[i].copy());
        }
        player.giveExperiencePoints(-exp);
    }

    protected void removeItem(ItemStack itemStack){
        itemStack.setCount(itemStack.getCount()-1);
    }

    @Override
    public boolean can_crush(Level level, Player player, InteractionHand usedHand) {
        return player.totalExperience >= reward.getExperience();
    }
}
