package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{//chromosome IS an arraylist of items
    private ArrayList<Item> items = new ArrayList<>();
    
    private int rng(){
        Random rand = new Random();
        return rand.nextInt(9);
    }

    public Chromosome(){}// no arg constructor for crossover method

    public Chromosome(ArrayList<Item> items){//normal chrom constructor
        for (Item item : items) {// for each item in the list do 50/50 if it is included or not
            if (rng() > 4)
                item.setIncluded(true);
            item.setIncluded(false);
        }
        mutate();//everytime the constructor is called, mutate the items list
        this.items = items;
    }

    public ArrayList<Item> getItems() {// this is never used?
        return items;
    }
    
    public Chromosome crossover(Chromosome otherParent){
        Chromosome returnChromosome = new Chromosome();//make new chromosome to return

        for (int i = 0; i < items.size(); i++) {     // foreach item in items, do 5050 to see if it gets this.item or other.item   
            if (rng()>4)
                returnChromosome.add(items.get(i));//set item at i to this.item
            else
                returnChromosome.add(otherParent.get(i));//set item at i to other.item
        }
        return returnChromosome;
    }

    public void mutate(){
        for (Item item : items) {
            if (rng() == 1) {// 0-9, so 1 is 10% to proc
                item.setIncluded(!item.isIncluded());//flip included value
            }
        }
}

    public int getFitness(){
        int weight = 0;
        int value = 0;
        long dummy = 0;

        for (int i=0; i<this.size()*1000; i++) {// this is to add load to each fitness assessment 
            dummy += i;
        }

        for (Item item : items) {
            if (item.isIncluded()) {
                weight += item.getWeight();
                value += item.getValue();
            }
        }

        if (weight>=10) {
            return 0;
        } else { return value; }
    }

    public int compareTo(Chromosome other){
        return Integer.compare(other.getFitness(), this.getFitness());
        // return -1 if this.chromosome is better than chromosome other, or +1 for the opposite, else 0 for equal chromosomes.
    }

    @Override
    public Item get(int i){
        return items.get(i);
    }
    @Override
    public int size(){ return items.size(); }//-------------------------------------------------- i shouldnt need this
    
    @Override
    public String toString(){
        String printStatement = "The items in this chromosome are: ";
        for (Item itemToPrint : items) { printStatement += itemToPrint.toString(); }
        return printStatement;
        }
    }





    //chromosome represents a list of the 7 items we COULD take with us
    //each item being shown in the chromosome as a T or F , T being were including it in that chromosome answer.
    //crossbreading works like this, each gene has a 50% to swap from a selection of 2 chromosomes
    //{1,2,3,4,5,6,7} use rand.next bool to get a 5050 for each gene