package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {//chromosome IS an arraylist of items
    private int rng() {
        Random rand = new Random();
        return rand.nextInt(9);
    }

    public Chromosome() {}

    public Chromosome(ArrayList<Item> itemList) {//normal chrom constructor
        for (Item item : itemList) {// for each item in the list do 50/50 if it is included or not
            Item itemCopy = new Item(item);
            if (rng() < 4)
                itemCopy.setIncluded(true);//else false
            this.add(itemCopy);
        }
    }

    public ArrayList<Item> getItems() {// this is never used?
        return this;
    }//never used

    public Chromosome crossover(Chromosome otherParent) {
        Chromosome returnChromosome = new Chromosome();//make new chromosome to return

        for (int i = 0; i < this.size(); i++) {     // foreach item in items, do 5050 to see if it gets this.item or other.item
            if (rng() >= 4)
                returnChromosome.add(this.get(i));//set item at i to this item
            else
                returnChromosome.add(otherParent.get(i));//set item at i to other item
        }
        return returnChromosome;
    }

    public void mutate() {
        for (Item item : this) {
            if (rng() == 1) {// 0-9, so 1 is 10% to proc
                item.setIncluded(!item.isIncluded());//flip included value
            }
        }
    }

    public int getFitness() {
        int weight = 0;
        int value = 0;
        for (Item item : this) {
            if (item.isIncluded()) {
                weight += item.getWeight();
                value += item.getValue();
            }
        }
        if (weight > 10) { return 0; }// if its over 10lbs we cant take it
        else { return value; }
    }

    @Override
    public int compareTo(Chromosome other) {
        if (this.getFitness()> other.getFitness())
            return 1;
        if (this.getFitness()< other.getFitness())
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        String printStatement = "";
        for (Item itemToPrint : this) {
            if (itemToPrint.isIncluded())
                printStatement += itemToPrint.toString();
        }
        return printStatement;
    }
}

//chromosome represents a list of the 7 items we COULD take with us
//each item being shown in the chromosome as a T or F , T being were including it in that chromosome answer.
//crossbreading works like this, each gene has a 50% to swap from a selection of 2 chromosomes
//{1,2,3,4,5,6,7} use rand.next bool to get a 5050 for each gene


//long dummy = 0;
//for (int i=0; i<this.size()*1000; i++) {// this is to add load to each fitness assessment
//    dummy += i;
//}