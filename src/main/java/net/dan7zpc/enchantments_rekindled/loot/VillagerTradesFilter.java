package net.dan7zpc.enchantments_rekindled.loot;

import net.dan7zpc.enchantments_rekindled.EnchantmentsRekindled;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//@EventBusSubscriber(modid = EnchantmentsRekindled.MOD_ID)
public class VillagerTradesFilter {
    //@SubscribeEvent
    public static void addTrades(VillagerTradesEvent event){
        VillagerProfession type = event.getType();
        if (type.equals(VillagerProfession.LIBRARIAN)) {
            removeTradesForRange(event, 1, 5);
        }else if (type.equals(VillagerProfession.WEAPONSMITH)) {
            removeTradesForRange(event,1, 5);
        }else if (type.equals(VillagerProfession.ARMORER)) {
            removeTradesForRange(event,4, 5);
        }else if (type.equals(VillagerProfession.TOOLSMITH)) {
            removeTradesForRange(event,3, 5);
        }else if (type.equals(VillagerProfession.FLETCHER)) {
            removeTradesForRange(event,4, 5);
        }else if (type.equals(VillagerProfession.FISHERMAN)) {
            removeTradesForRange(event,3, 3);
        }
    }
    private static void removeTradesForRange(VillagerTradesEvent event, int fromLvl, int toLvl){
        for(int level = fromLvl; level <= toLvl; level++){
            event.getTrades().get(level).clear();
        }
    }
}
