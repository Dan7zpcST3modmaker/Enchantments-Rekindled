package net.dan7zpc.enchantments_rekindled.abyss;

import net.dan7zpc.enchantments_rekindled.util.PriceList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

import java.util.logging.Level;

public record VoidEventsSet(PriceList<VoidEvent> events) {
    public void call(ServerLevel level, BlockPos pos, RandomSource random){
        int maxPrice = events.getFullPrice();
        int weight = random.nextIntBetweenInclusive(1,maxPrice);
        VoidEvent event = events.getMostExpensiveAvailable(weight).object();
        event.call(level,pos);
    }
}
