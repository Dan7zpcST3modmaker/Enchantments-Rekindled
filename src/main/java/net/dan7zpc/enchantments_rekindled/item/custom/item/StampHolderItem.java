package net.dan7zpc.enchantments_rekindled.item.custom.item;

import net.dan7zpc.enchantments_rekindled.enchantment.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;

public class StampHolderItem extends QuickTooltipItem{
    public StampHolderItem(Properties properties, String _tooltip, String _shift_tooltip, String _controlTooltip) {
        super(properties, _tooltip, _shift_tooltip, _controlTooltip);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
