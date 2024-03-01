package com.metrostate.ics499.ers;

import java.util.ArrayList;
import java.util.List;

enum locType {
    Shelter,
    Foster
}
enum speciesAvailable {
    Cat,
    Dog,
    Rabbit,
    Bird
}

public class Location {

    private final locType type;
    private final int id;
    private String name;
    private String address;
    private int maxCapacity;
    private List<speciesAvailable> species;
    private List<Animal> animals;

    public Location(locType type, int id, String name, String address, int maxCapacity, List<speciesAvailable> species) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.species = species;
        animals = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setSpecies(List<speciesAvailable> species) {
        this.species = species;
    }

    public void addSpecies(speciesAvailable addition){
        species.add(addition);
    }

    public void removeSpecies(speciesAvailable takeaway){
        species.remove(takeaway);
    }

    public List<Animal> getAnimals(){
        return animals;
    }

    public boolean atCapacity(){
        return maxCapacity <= animals.size();
    }
}
