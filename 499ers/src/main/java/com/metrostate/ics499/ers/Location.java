package com.metrostate.ics499.ers;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class Location implements Updatable<Location> {
    private static int idCounter = 10;
    private int id;
    private Types.LocType type;
    private String name;
    private String address;
    private List<Types.SpeciesAvailable> species;
    private List<Animal> animals;
    private int maxCapacity;

    public static void setStartid(int start) {
        idCounter = start;
    }

    // Constructor for reconstructing a Location from the database
    public Location(int id, Types.LocType type, String name, String address, int maxCapacity, List<Types.SpeciesAvailable> species) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.species = species;
        animals = new ArrayList<>();
    }

    // Constructor for creating a new entry into the database
    public Location(Types.LocType type, String name, String address, int maxCapacity, List<Types.SpeciesAvailable> species) {
        this.id = idCounter++;
        this.type = type;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.species = species;
        animals = new ArrayList<>();
    }

    /**
     * Adds an animal to this location and updates the database. Returns true
     * if the animal is not null and the location is not at max capacity.
     *
     * @param addition animal to be added
     * @return true if the animal was added; false otherwise
     */
    public boolean addAnimal(@org.jetbrains.annotations.NotNull Animal addition){
        if (species.contains(addition.getSpecies()) && !atCapacity()) {
            DBAdapter.insert(addition, this);
            return animals.add(addition);
        } else {
            return false;
        }

    }

    public boolean removeAnimal(Animal takeaway){
        return animals.remove(takeaway);
    }

    public boolean atCapacity(){
        return maxCapacity <= animals.size();
    }

    /**
     * Adds an available species to this location and updates the database.
     * Returns true if the species was added; false otherwise
     *
     * @param addition species to be added to availability
     * @return true if the species was added; false otherwise
     */
    public boolean addSpecies(Types.SpeciesAvailable addition){
        if (!species.contains(addition)) {
            DBAdapter.insert(addition, this);
            return species.add(addition);
        } else {
            return false;
        }
    }

    public boolean removeSpecies(Types.SpeciesAvailable takeaway){
        return species.remove(takeaway);
    }

    // GETTERS AND SETTERS ############################################################################################
    // ################################################################################################################

    public int getId(){
        return id;
    }
    public Types.LocType getType(){
        return type;
    }

    //new update for UpdateLocationController by Lytong
    public void setType(Types.LocType type) {
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Types.SpeciesAvailable> getSpecies(){
        return species;
    }

    public void setSpecies(List<Types.SpeciesAvailable> species) {
        if (species != null && this.species != null) {
            this.species.clear();
            this.species.addAll(species);
        }
    }

    public List<Animal> getAnimals(){
        return animals;
    }

    public void setAnimals(List<Animal> animals){
        if (animals != null) {
            this.animals.clear();
            this.animals.addAll(animals);
        }
    }

    public String toString(){
        return String.format("LOCATION [ id = %d, name = %s, Type = %s, address = %s, capacity = %d, species = %s, animal count = %d]",
                id, name, type.toString(), address, maxCapacity, species.toString(), animals.size());
    }

    @Override
    public boolean update(Location update) {
        if (DBAdapter.update(this, update)) {
            this.setId(update.getId());
            this.setType(update.getType());
            this.setName(update.getName());
            this.setAddress(update.getAddress());
            this.setMaxCapacity(update.getMaxCapacity());
            this.setSpecies(update.getSpecies());
            return true;
        } else {
            return false;
        }
    }
}
