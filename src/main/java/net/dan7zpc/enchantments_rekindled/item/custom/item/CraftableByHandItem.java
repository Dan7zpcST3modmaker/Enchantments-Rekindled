package net.dan7zpc.enchantments_rekindled.item.custom.item;

import net.dan7zpc.enchantments_rekindled.item.custom.Reward;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CraftableByHandItem extends CrushRecipeItem{
    public CraftableByHandItem(Properties properties, int exp_cost) {
        super(properties, exp_cost);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltip(stack,context,tooltipComponents,tooltipFlag,"craftable_by_hand");
        fastDynamicTooltip(stack,context,tooltipComponents,tooltipFlag,"exp_cost",""+this.reward.getExperience());
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!player.isCrouching()){return super.use(level, player, usedHand);}
        crush(level, player, usedHand);
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
