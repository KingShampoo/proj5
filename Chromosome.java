package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{//chromosome IS an arraylist of items
    private ArrayList<Item> items = new ArrayList<Item>();
    //private static long dummy = 0;

    private int rng(){
        Random rand = new Random();
        int rng = rand.nextInt(10);
        return rng;
    }
    public Chromosome( ArrayList<Item> items, int index) {
        this.items = items;
        items.get(index).setIncluded(true);
    }

    public Chromosome( ArrayList<Item> items, ArrayList<Integer> indexList) {

        this.items = items;
        for (int index:indexList) {
            items.get(index).setIncluded(true);
        }
    }

    public Chromosome(ArrayList<Item> items){

        for (int i = 0; i < items.size(); i++) {//i think the problem starts here #fixme
            if (rng() > 5) {
                items.get(i).setIncluded(true);
            }
            items.get(i).setIncluded(false);
        }
        //add list of items to arraylist
        //for each item in list 50%chance to trigger included
        this.items = items;
        mutate();
    }

    public Chromosome(){}
    //takes an array list and turns it into a chromosome

    public List<Item> getItems() {
        return items;
    }

    public Chromosome crossover(Chromosome parentA,Chromosome parentB){
        Chromosome returnChromosome = new Chromosome();

        for (int i = 0; i < items.size(); i++) {
            if (rng()>5)
                returnChromosome.add(parentA.get(i));
            else
            returnChromosome.add(parentB.get(i));
        }
        //creates a child from 2 chromosomes
        // 2 chromosomes are this.chromosome and other
        //use if rng>5 chromosome other gene is passed to child, else this chromosome gene is passed
        return returnChromosome;
    }

    public void mutate(){
        Item token;
        for (int i = 0; i < items.size(); i++) {
            if (rng()<=1){
            token = items.get(i);
            if (token.isIncluded()==true)
                token.setIncluded(false);
            }
        }
    }

    public int getFitness(){
        int weight = 0;
        int value = 0;
        int fitness;

     //   dummy = 0;
      //  for (int i=0; i<this.size()*1000; i++) {
      //      dummy += i;
      //  }

        for (int i = 0; i < items.size(); i++) {
            weight += items.get(i).getWeight();
        }
        for (int i = 0; i < items.size(); i++) {
            value += items.get(i).getValue();
        }

        if (weight>10) {
            fitness = 0;
        } else {
            fitness = value;
        }
        return fitness;
    }

    public int compareTo(Chromosome other){
        int returnValue  = 0;
        if (this.getFitness()> other.getFitness())
            returnValue = -1;
        if (this.getFitness()< other.getFitness())
            returnValue = 1;

        return returnValue;// return -1 if chromosome A is better than chromosome B, or +1 for the opposite, else 0 for equal chromosomes.
    }
    @Override
    public Item get(int i){
        return items.get(i);
    }
    @Override
    public String toString(){
        String printStatement = "The items in this chromosome are: ";
        for (int i = 0; i < items.size(); i++) {
            Item itemToPrint = items.get(i);
            printStatement += itemToPrint.toString();
        }
         return printStatement;
        }
    }





    //chromosome represents a list of the 7 items we COULD take with us
    //each item being shown in the chromosome as a T or F , T being were including it in that chromosome answer.
    //crossbreading works like this, each gene has a 50% to swap from a selection of 2 chromosomes
    //{1,2,3,4,5,6,7} use rand.next bool to get a 5050 for each gene