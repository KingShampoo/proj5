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
            listToPass.add(new Chromosome(items));//generates first 10 chromosomes
        }
        return listToPass;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        final String fileName = "C://vm//more_items.txt";
        final int populationSize = 100;//        ---- change path and epochs up here if desired----
        final int numberOfEPOCHS = 5000;

        ArrayList<Item> itemList = new ArrayList(readData(fileName));//read in from file to arraylist items
        ArrayList<Chromosome> currentGeneration = initializePopulation(itemList,populationSize); // makes a starting population of int populationSize
        ArrayList<Chromosome> nextGeneration = new ArrayList<>();//makes empty list to reuse later

        for (int i = 0; i < numberOfEPOCHS; i++) {//mutate and shed bad chromosomes number of EPOCH times
            // for each chromosome in current gen, add to the next generation
            nextGeneration.addAll(currentGeneration);
            //step 3 create 5 children using crossover, add to next gen population pool

            for (int j = 0; j < (currentGeneration.size() / 2); j++) {
                Chromosome parentA = currentGeneration.get(i);//starts at 0
                Chromosome parentB = currentGeneration.get(currentGeneration.size()/2+i);// starts in middle 
                Chromosome child = parentA.crossover(parentB);
                nextGeneration.add(child);
            }

            for (int j = 0; j < (nextGeneration.size()); j++)
                nextGeneration.get(i).mutate();

            //sort next gen according to fitness
            Collections.sort(nextGeneration);
            currentGeneration.clear();// wipe current gen and replace with next gen

            currentGeneration.addAll(nextGeneration);
            nextGeneration.clear();//clear next gen and loop
        }

        for (Chromosome c : currentGeneration) {
            System.out.println(c.getFitness());
        }
        System.out.println(currentGeneration.get(0).toString() + "is the best chromosome");
    }
}
