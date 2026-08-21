package net.dan7zpc.enchantments_rekindled.enchantment;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = EnchantmentsRekindled.MOD_ID, value = Dist.CLIENT)
public class EnchantmentIconRenderer {
    private static int tooltipWidth = 0;
    private static int tooltipHeight = 0;
    private static final Map<ResourceLocation,ResourceLocation> ENCHANTMENT_MAP = new HashMap<>();
    static {
        for (int i = 0; i < 26; i++) {
            ResourceLocation enchantmentKey = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID, "galactic/" + (char)('a'+i));
            ResourceLocation enchantmentTemplateKey = ResourceLocation.fromNamespaceAndPath(EnchantmentsRekindled.MOD_ID, "galactic/template/t_" + (char)('a'+i));

            ENCHANTMENT_MAP.put(
                    enchantmentKey,
                    ResourceLocation.parse(EnchantmentsRekindled.MOD_ID+ ":textures/gui/enchantments/galactic/"+(char)('a'+i)+".png")
            );
            ENCHANTMENT_MAP.put(
                    enchantmentTemplateKey,
                    ResourceLocation.parse(EnchantmentsRekindled.MOD_ID+ ":textures/gui/enchantments/galactic/template/"+(char)('a'+i)+".png")
            );
        }
    }


    private static final int ICON_SIZE = 16;
    private static final int ICON_OFFSET = 4;



    @SubscribeEvent
    public static void onTooltipRender(RenderTooltipEvent.Pre event){
        ItemStack stack = event.getItemStack();
        if(stack == null || stack.isEmpty()) return;

        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if(enchantments==null||enchantments.isEmpty())return;

        Holder<Enchantment> enchantment = enchantments.keySet().iterator().next();

        ResourceLocation enchKey = enchantment.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        if(enchKey == null) return;

        ResourceLocation iconPath = ENCHANTMENT_MAP.get(enchKey);
        if(iconPath == null) return;

        GuiGraphics graphics = event.getGraphics();

        int tooltipX = event.getX()+180;
        int tooltipY = event.getY()-8;

        int iconX = tooltipX + tooltipWidth;// + ICON_OFFSET;
        int iconY = tooltipY + (tooltipHeight/2);// + ICON_OFFSET/2;
        graphics.blit(
                iconPath,
                iconX,iconY,
                800,0,0,16,16,16,16
        );
        //
    }
}
