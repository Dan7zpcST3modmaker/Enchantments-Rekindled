package net.dan7zpc.enchantments_rekindled.item.custom.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Objects;

public class QuickTooltipItem extends CustomModItem{
    protected String tooltipText;
    protected String shiftTooltipText;
    protected String controlTooltipText;

    public QuickTooltipItem(Properties properties, String _tooltip, String _shift_tooltip, String _controlTooltip) {
        super(properties);
        this.tooltipText = _tooltip;
        this.shiftTooltipText = _shift_tooltip;
        this.controlTooltipText = _controlTooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!Objects.equals(tooltipText, "")){tooltip(stack,context,tooltipComponents,tooltipFlag, tooltipText);}
        if (!Objects.equals(shiftTooltipText, "")){shiftTooltip(stack,context,tooltipComponents,tooltipFlag, shiftTooltipText);}
        if (!Objects.equals(controlTooltipText, "")){controlTooltip(stack,context,tooltipComponents,tooltipFlag, controlTooltipText);}
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public String getTooltipText() {
        return tooltipText;
    }

    public String getControlTooltipText() {
        return controlTooltipText;
    }

    public String getShiftTooltipText() {
        return shiftTooltipText;
    }
}
