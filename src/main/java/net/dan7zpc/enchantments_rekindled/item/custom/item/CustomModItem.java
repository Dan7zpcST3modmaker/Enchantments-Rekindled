package net.dan7zpc.enchantments_rekindled.item.custom.item;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.lang.model.util.Elements;
import java.util.List;

public class CustomModItem extends Item {
    public CustomModItem(Properties properties) {
        super(properties);
    }

    public void literalTooltip(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String text){
        tooltipComponents.add(Component.literal(text));
    }

    public void tooltip(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
    }
    public void fastDynamicTooltip(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey,String data){
        tooltipComponents.add(
                Component.literal(Component.translatable("tooltip." + EnchantmentsRekindled.MOD_ID + "." + translationKey).getString()+
                        " §a["+data+"]§r")
        );
    }

    public void shiftTooltip(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        if(Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
        }else {
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+".hold_shift"));
        }
    }

    public void controlTooltip(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, String translationKey){
        if(Screen.hasControlDown()){
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+"."+translationKey));
        }else {
            tooltipComponents.add(Component.translatable("tooltip."+ EnchantmentsRekindled.MOD_ID+".hold_control"));
        }
    }
}
