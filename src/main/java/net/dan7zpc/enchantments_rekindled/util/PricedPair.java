package net.dan7zpc.enchantments_rekindled.util;

public record PricedPair(int cost, Object object) {
    public boolean isAvailable(int budget){
        return budget >= cost;
    }
    public int maxAvailableAmount(int budget){
        return budget/cost;
    }
    public int getPriceForAmount(int amount){
        return cost*amount;
    }
}
