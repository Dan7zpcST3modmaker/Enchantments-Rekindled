package net.dan7zpc.enchantments_rekindled.util;

public record PricedPair<T>(int cost, T object) {
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
