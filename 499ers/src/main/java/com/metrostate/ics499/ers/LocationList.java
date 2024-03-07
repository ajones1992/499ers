package com.metrostate.ics499.ers;

import java.util.ArrayList;

/**
 * List of all available locations (Shelters or Foster homes) in the system. Also
 * responsible for transferring animals between shelters.
 */
public class LocationList {

    private ArrayList<Location> locations;

    /**
     * Adds a Shelter or Foster location ot the list of available locations.
     *
     * @param location location to be added
     * @return true if successfully added; false otherwise.
     */
    public boolean addLocation(Location location) {
        return locations.add(location);
    }

    /**
     * removes a Shelter or Foster location ot the list of available locations.
     *
     * @param location location to be removed
     * @return true if successfully removed; false otherwise.
     */
    public boolean removeLocation(Location location) {
        return locations.remove(location);
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
        if (locations.contains(origin) && locations.contains(destination)
                && origin.getAnimals().contains(animal)
                && destination.getSpecies().contains(animal.getSpecies())) {
            animal.setCode(Types.ExitCode.inTransit);
            origin.removeAnimal(animal);
            destination.addAnimal(animal);
            return true;
        } else {
            return false;
        }
    }
}
