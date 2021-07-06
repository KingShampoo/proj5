package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GeneticAlgorithm {
    public static ArrayList<Item> readData(String fileName) throws FileNotFoundException {//reads data file and uses tokens to build an obj (item)
        ArrayList<Item> itemList = new ArrayList<>();
        String itemToken;
        double weightToken;
        int valueToken;

        try {
            Scanner inFile1 = new Scanner(new File(fileName));
            inFile1.useDelimiter(",|\n");//separates tokens by commas and enter keys
            while (inFile1.hasNext()) {
                itemToken = inFile1.next().trim();
                weightToken = Double.parseDouble(inFile1.next().trim());//no white space allowed!
                valueToken = Integer.parseInt(inFile1.next().trim());
                Item newItem = new Item(itemToken,weightToken,valueToken);
                itemList.add(newItem);//creates item from the tokens and then adds to a list to then make a total item list

            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return itemList;
    }

    public static ArrayList<Chromosome> initializePopulation( ArrayList<Item> items, int populationSize) {//sets up all the chromosomes with randomly selected T's and F's
        ArrayList<Chromosome> listToPass = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Chromosome chromie = new Chromosome(items);
            listToPass.add(chromie);//generates first generation of chromosomes
        }
        return listToPass;
    }

    public static void main(String[] args) throws FileNotFoundException {
        final String fileName = "items.txt";
        final int populationSize = 10;//        ---- change path + population + epochs up here if desired----
        final int numberOfEPOCHS = 50;
        Random rand = new Random();

        ArrayList<Item> itemList = new ArrayList(readData(fileName));//read in from file to arraylist items
        ArrayList<Chromosome> currentGeneration = initializePopulation(itemList,populationSize); // makes a starting population of int populationSize
        ArrayList<Chromosome> nextGeneration = new ArrayList<>();//makes empty list to reuse later

        for (int i = 0; i < numberOfEPOCHS; i++) {//mutate and shed bad chromosomes number of EPOCH times
            nextGeneration.addAll(currentGeneration);// for each chromosome in current gen, add to the next generation

            for (int j = 0; j < (currentGeneration.size() / 2); j++) {//step 3 create children using crossover, add to next gen population pool
                Chromosome parentA = currentGeneration.get(j);//starts at 0
                Chromosome parentB = currentGeneration.get(currentGeneration.size() / 2 + j);// starts in middle
                Chromosome child = parentA.crossover(parentB);
                nextGeneration.add(child);//add child
            }

            for (int j = 0; j < (nextGeneration.size()); j++){

                if (rand.nextInt(9)==1)//1 in 10 chance
                nextGeneration.get(j).mutate();
            }
            Collections.sort(nextGeneration);//sort next gen according to fitness
            currentGeneration.clear();// wipe current gen to replace with next gen

            for (int j = 0; j < 10; j++) {//take top 10 and add to next current gen
                currentGeneration.add(nextGeneration.get(j));
            }
            nextGeneration.clear();//clear next gen and loop
        }   
        long value = 0;
        for (Item item: currentGeneration.get(0).getItems()) {
            if (item.isIncluded()){
                value+=item.getValue();
            }
        }
        System.out.println("the best chromosome has :"+currentGeneration.get(0).toString()+"\nWith a value of :"+value);
    }

}