package com.company;

public class Item  {
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
        if (included) this.included = true;
        else this.included = false;
    }

    public String toString() {
        return " " + this.name + " ( " + this.weight + " lbs, $" + this.value + " )";//prints fields n stuff
    }

    public Item(Item other) {
        this.name = other.getName();
        this.weight = other.getWeight();//do i even need this??????
        this.value = other.getValue();
        // copys another item using the other items fields
    }
}