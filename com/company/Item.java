package com.company;

public class Item extends Chromosome {
    private final String name;
    private final double weight;
    private final int value;
    private boolean included;

    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isIncluded() {
        return this.included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public String toString() {
        return " " + this.name + " ( " + this.weight + " lbs, $" + this.value + " )";//prints fields n stuff
    }

    public Item(Item other) {// copy constructor
        this.name = other.getName();
        this.weight = other.getWeight();//do i even need this??????
        this.value = other.getValue();
        this.included = other.included;


    }
}