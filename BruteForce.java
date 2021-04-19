package com.company;

import java.util.ArrayList;

public class BruteForce {










public static ArrayList<Item> getOptimalSet(ArrayList<Item> items, int index){
    ArrayList<Item> arrList = new ArrayList<>();

    Item currentItem = items.get(index);
    if (index == 1){
        for (Item item:items) {
            Chromosome c = new Chromosome(item, index);
        }
    } else {
        for (int i = 0; i < items.size()-1; i++) {
            getOptimalSet(items,index-1);

        }
    }
    getOptimalSet(items, index);
    return arrList;
}

}

