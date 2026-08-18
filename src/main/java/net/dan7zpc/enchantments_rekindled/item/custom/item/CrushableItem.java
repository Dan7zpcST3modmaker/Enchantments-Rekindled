package net.dan7zpc.enchantments_rekindled.item.custom.item;

import net.dan7zpc.enchantments_rekindled.item.custom.ICrushable;
import net.dan7zpc.enchantments_rekindled.item.custom.Reward;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CrushableItem extends CustomModItem implements ICrushable {
    protected Reward reward;
    public CrushableItem(Properties properties,Reward _reward) {
        super(properties);
        this.reward = _reward;
    }

    public void setReward(Reward _reward) {
        this.reward = _reward;
    }

    @Override
    public void crush(Level level, Player player, InteractionHand usedHand) {
        removeItem(player.getItemInHand(usedHand));
        level.playSound(player, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS,0.4f,1f);
        giveTheRewardTo(player);
    }

    @Override
    public boolean can_crush(Level level, Player player, InteractionHand usedHand) {
        return true;
    }

    protected void giveTheRewardTo(Player player){
        ItemStack[] pool = reward.getItems();
        int exp = reward.getExperience();
        player.giveExperiencePoints(exp);
        if(pool.length == 0){return;}
        for (int i = 0; i < pool.length; i++) {
            player.addItem(pool[i]);
        }
    }

    protected void removeItem(ItemStack itemStack){
        itemStack.setCount(itemStack.getCount()-1);
    }
}
