package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GeneticAlgorithm {
        public static ArrayList<Item> readData(String fileName) throws FileNotFoundException {//reads data file and uses tokens to build an obj (item)
            ArrayList<Item> itemList = new ArrayList<>();
            final String path = fileName;
            String itemToken;
            double weightToken;
            int valueToken;

            try {
                Scanner inFile1 = new Scanner(new File(path));
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

    public static Collection<Chromosome> sortingMethod (ArrayList<Chromosome> nextGeneration){
            Collection<Chromosome> returnCollection = new ArrayList<>();
            Collections.sort(nextGeneration, (o1, o2) -> {
                int returnvalue = o1.getFitness();
                if (o2.getFitness()> o1.getFitness())//return highest fitness when comparing two
                    returnvalue = o2.getFitness();

                return returnvalue;
            });
            return returnCollection;
    }

    public static void main(String[] args) throws FileNotFoundException {
        final String fileName = "C://vm//more_items.txt";
        final int populationSize = 10;
        final int numberOfEPOCHS = 100;

        ArrayList<Item> itemList = new ArrayList(readData(fileName));

        ArrayList<Chromosome> currentGeneration = initializePopulation(itemList,populationSize); // makes a starting population of int populationSize// start with 10 chromosomes
        ArrayList<Chromosome> nextGeneration = new ArrayList<>();

        for (int i = 0; i < numberOfEPOCHS; i++) {//iterate how many times

            for (int j = 0; j < currentGeneration.size(); j++)
                nextGeneration.add(currentGeneration.get(j));
            //step 3 create 5 children using crossover, add to next gen population pool

            for (int j = 0; j < (currentGeneration.size() / 2); j++) {
                Chromosome parentA = currentGeneration.get(i);
                Chromosome parentB = currentGeneration.get(currentGeneration.size()/2+i);
                Chromosome child = parentA.crossover(parentA, parentB);
                nextGeneration.add(child);
            }

            //step 4 mutate 10%(found in mutation method in chromosome) and add to next gen pool
            for (int j = 0; j < (nextGeneration.size()); j++)
                nextGeneration.get(i).mutate();

            //sort next gen according to fitness
            nextGeneration = (ArrayList<Chromosome>) sortingMethod(nextGeneration);

            currentGeneration.clear();
            for (int j = 0; j < nextGeneration.size(); j++) {
                currentGeneration.add(nextGeneration.get(j));
            }
            nextGeneration.clear();
            //clear current population and take top 10 of next gen to replace it as current generation
        }
        for (Chromosome c : currentGeneration) {
            System.out.println(c.getFitness());
        }
    }
}
