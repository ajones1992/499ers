package com.metrostate.ics499.ers;

import java.util.*;

/**
 * List of all available locations (Shelters or Foster homes) in the system. Also,
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

    public Animal getAnimal(int id){
        for (Animal ani:getAllAnimals()) {
            if(ani.getId()==id)
                return ani;
        }
        return null;
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

    public List<Record> getAllRecords(){
        List<Record> records = new ArrayList<>();
        List<Location> shelters = new ArrayList<>(mapOfLocations.values());

        for (Animal animal : getAllAnimals()) {
            records.addAll(animal.getRecords());
        }

        return records;
    }

    public Record getRecord(int id){
        for (Record rec:getAllRecords()) {
            if(rec.getId()==id)
                return rec;
        }
        return null;
    }

    /**
     * Adds a Shelter or Foster location ot the list of available locations.
     * Updates the database.
     *
     * @param location location to be added
     */
    public void addLocation(Location location) {
      if (mapOfLocations.putIfAbsent((Integer)location.getId(), location) == null) {
          DBAdapter.insert(location);
      }
    }

    /**
     * Loads a database table of Locations into the LocationList.
     */
    public void loadDatabaseIntoMap(){
        List<Location> locations = DBAdapter.getAllLocations();
        setStartIds(locations);
        for (Location loc: locations) {
            mapOfLocations.putIfAbsent((Integer)loc.getId(), loc);
        }
    }

    private void setStartIds(List<Location> locations) {
        int locationStart = 0;
        int animalStart = 0;
        int recordStart = 0;
        for (Location l : locations) {
            locationStart = Math.max(locationStart, l.getId());
            for (Animal a : l.getAnimals()) {
                animalStart = Math.max(animalStart, a.getId());
                for (Record r : a.getRecords()) {
                    recordStart = Math.max(recordStart, r.getId());
                }
            }
        }
        Location.setStartid(++locationStart);
        Animal.setStartid(++animalStart);
        Record.setStartid(++recordStart);
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        for (Location loc : getAllLocations()) {
            str.append("-" + loc.toString() + "\n");
            str.append("\tSPECIES AVAILABLE:\n");
            for (Types.SpeciesAvailable s: loc.getSpecies()) {
                str.append("\t\t-" + s.toString() + "\n");
            }
            str.append("\tANIMALS:\n");
            for (Animal a: loc.getAnimals()) {
                str.append("\t\t-" + a.toString() + "\n");
                str.append("\t\t\tRECORDS:\n");
                for(Record r : a.getRecords()) {
                    str.append("\t\t\t-" + r.toString() + "\n");
                }
            }
        }
        return str.toString();
    }
}
