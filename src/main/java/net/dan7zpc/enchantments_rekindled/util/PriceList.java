package net.dan7zpc.enchantments_rekindled.util;

import java.util.Arrays;
import java.util.List;

public class PriceList {
    private PricedPair[] catalogue;
    public static PricedPair cheapestPricedPair = new PricedPair(Integer.MIN_VALUE,new Object());
    public PriceList(PricedPair... pairs){
        catalogue = pairs;
        sort();
    }

    public PricedPair[] getEntries(){
        return this.catalogue;
    }

    public List<PricedPair> getMostExpensiveProductSetAvailable(int budget){
        List<PricedPair> shoppingList = new java.util.ArrayList<>(List.of());
        for (int i = catalogue.length-1; i >= 0; i--) {
            PricedPair currentlyChecking = catalogue[i];
            int maxAvailable = currentlyChecking.maxAvailableAmount(budget);
            if(maxAvailable>0){for (int j = 0; j < maxAvailable; j++) {shoppingList.add(currentlyChecking);}}
            budget -= currentlyChecking.getPriceForAmount(maxAvailable);
        }
        return shoppingList;
    }

    public void sort(){
        for(int i = 0; i < catalogue.length; i++){
            boolean swapped = false;
            for(int j = 0; j < (catalogue.length-i-1); j++){
                if (catalogue[j].cost() > catalogue[j+1].cost()){
                    var sponge = catalogue[j+1];
                    catalogue[j+1] = catalogue[j];
                    catalogue[j] = sponge;
                    swapped = true;
                }
            }
            if(!swapped){break;}
        }
    }

    public PricedPair getMostExpensiveAvailable(int budget){
        PricedPair top = cheapestPricedPair;
        for (int i = 0; i < catalogue.length; i++) {
            PricedPair currentPair = catalogue[i];
            if (currentPair.cost() > top.cost() && currentPair.isAvailable(budget))top = currentPair;
        }
        return top;
    }
}
