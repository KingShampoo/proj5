package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.company.GeneticAlgorithm.*;

public class Worker implements Runnable {

    private final String fileName = "C://vm//more_items.txt";
    private final int populationSize = 10;
    private final int numberOfEPOCHS = 100;

    @Override
    public void run() {
        ArrayList<Item> itemList = null;
        try {
            itemList = new ArrayList(readData(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
