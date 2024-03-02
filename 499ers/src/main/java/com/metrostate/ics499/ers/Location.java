package com.metrostate.ics499.ers;

import java.util.ArrayList;
import java.util.List;

enum LocType {
    Shelter,
    Foster
}
enum SpeciesAvailable {
    Cat,
    Dog,
    Rabbit,
    Bird
}

public class Location {
    private static int idCounter = 0;
    private final int id;
    private final LocType type;
    private String name;
    private String address;
    private List<SpeciesAvailable> species;
    private List<Animal> animals;
    private int maxCapacity;

    public Location(LocType type, String name, String address, int maxCapacity, List<SpeciesAvailable> species) {
        this.id = idCounter++;
        this.type = type;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.species = species;
        animals = new ArrayList<>(); // Do we just want to add animals separately? What if we have a list of animals known to that shelter?
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxCapacity(){
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setSpecies(List<SpeciesAvailable> species) {
        this.species = species;
    }

    public void addSpecies(SpeciesAvailable addition){
        species.add(addition);
    }

    public void removeSpecies(SpeciesAvailable takeaway){
        species.remove(takeaway);
    }
    public List<SpeciesAvailable> getSpecies(){
        return species;
    }

    public void addAnimal(Animal addition){
        animals.add(addition);
    }

    public void removeAnimal(Animal takeaway){
        animals.remove(takeaway);
    }

    public void setAnimals(List<Animal> animals){
        this.animals.clear();
        this.animals.addAll(animals);
    }

    public List<Animal> getAnimals(){
        return animals;
    }

    public boolean atCapacity(){
        return maxCapacity <= animals.size();
    }
}
