package com.metrostate.ics499.ers;

import java.util.*;

/**
 * List of all available locations (Shelters or Foster homes) in the system. Also
 * responsible for transferring animals between shelters.
 */
public class LocationList {
    private final Map<Integer, Location> mapOfLocations = new HashMap<>();

    public boolean containsLocation(int id){
        return mapOfLocations.containsKey((Integer)id);
    }

    public Location getLocation(int id){
        return mapOfLocations.get((Integer)id);
    }

    public List<Location> getAllLocations(){
        return new ArrayList<>(mapOfLocations.values());
    }

    public void showLocations(){
        for (Location loc: mapOfLocations.values()) {
            System.out.println(loc.getId() + ": " + loc.getName() + "\n");
        }
    }

    /**
     * Generates a list of all Animals in all Locations
     *
     * @return List<Animal> in all Locations
     */
    public List<Animal> getAllAnimals(){
        List<Animal> animals = new ArrayList<>();
        List<Location> shelters = new ArrayList<>(mapOfLocations.values());

        for (Location shelter : shelters) {
            animals.addAll(shelter.getAnimals());
        }

        return animals;
    }

    /**
     * Adds a Shelter or Foster location ot the list of available locations.
     *
     * @param location location to be added
     */
    public void addLocation(Location location) {
        mapOfLocations.putIfAbsent((Integer)location.getId(), location);
    }

    /**
     * removes a Shelter or Foster location ot the list of available locations.
     *
     * @param id of location to be removed
     * @return true if successfully removed; false otherwise.
     */
    public Location removeLocation(int id) {
        return mapOfLocations.remove((Integer)id);
    }

    /**
     * Moves an animal from the origin location to the destination location.
     * Ensures that both locations are contained in the available locations
     * and that the animal belongs to the origin location and can be accepted
     * by the destination location.
     *
     * @param animal the animal in transit
     * @param origin the location currently housing the animal
     * @param destination the location the animal will be moved to
     * @return true if the animal was successfully transferred; false otherwise
     */
    public boolean transferAnimal(Animal animal, Location origin, Location destination) {
        if (mapOfLocations.containsValue(origin) && mapOfLocations.containsValue(destination)
                && origin.getAnimals().contains(animal)
                && destination.getSpecies().contains(animal.getSpecies())) {
            animal.setCode(Types.ExitCode.IN_TRANSIT);
            origin.removeAnimal(animal);
            destination.addAnimal(animal);
            return true;
        } else {
            return false;
        }
    }
}
