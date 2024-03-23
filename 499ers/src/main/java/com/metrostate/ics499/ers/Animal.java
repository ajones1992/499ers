package com.metrostate.ics499.ers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Animal {
    private static int idCounter = 1;
    private int id;
    private String name;
    private Types.SpeciesAvailable species;
    private String breed;
    private double weight;
    private LocalDate DOB;
    private LocalDate intakeDate;
    private LocalDate exitDate;
    private Types.ExitCode code;
    private String notes;
    private List<Record> records;
    // Assuming Location class has an ID field of type int
    private int locationID; // Add this to match ERD

    public Animal(String name, Types.SpeciesAvailable species, String breed, double weight, LocalDate dob, LocalDate intakeDate, String notes) {
        this.id = idCounter++;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.weight = weight;
        this.DOB = dob;
        this.intakeDate = intakeDate;
        this.notes = notes;
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        if (record != null) {
            this.records.add(record);
        }
    }

    public void removeRecord(Record record) {
        this.records.remove(record);
    }

    public void updateRecord(Record updatedRecord) {
        if (updatedRecord == null) {
            throw new IllegalArgumentException("Record to update cannot be null");
        }

        for (int i = 0; i < this.records.size(); i++) {
            // Check if this record has the same ID as the updatedRecord
            if (this.records.get(i).getId() == updatedRecord.getId()) {
                // If found, replace it with the updatedRecord
                this.records.set(i, updatedRecord);
                break; // Stop the loop after updating
            }
        }
    }

    // Add this method to match the class diagram
    public void updateAnimalEntry() {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        if (species != null) {
            this.species = species;
        }
        if (breed != null && !breed.trim().isEmpty()) {
            this.breed = breed;
        }
        if (weight > 0) {
            this.weight = weight;
        }
        if (DOB != null) {
            this.DOB = DOB;
        }
        if (intakeDate != null) {
            this.intakeDate = intakeDate;
        }
        if (exitDate != null) {
            this.exitDate = exitDate;
        }
        if (code != null) {
            this.code = code;
        }
        if (notes != null && !notes.trim().isEmpty()) {
            this.notes = notes;
        }
    }

    public void synchronizeWithDatabase(DBAdapter dbAdapter) {
        boolean success = dbAdapter.updateAnimal(this);
        if (!success) {
            // Handle failure (throw an exception, log an error, etc.)
            System.err.println("Failed to update animal in database.");
        }
    }

    public void handleAdoption(LocalDate adoptionDate, Types.ExitCode exitCode) {
        this.exitDate = adoptionDate;
        this.code = exitCode; // Assuming Types.ExitCode.ADOPT represents a successful adoption
    }

    // Method to retrieve the current location of this animal
    public Location getCurrentLocation(DBAdapter dbAdapter) {
        return dbAdapter.getLocationById(this.locationID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Types.SpeciesAvailable getSpecies() {
        return species;
    }

    public void setSpecies(Types.SpeciesAvailable species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public LocalDate getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(LocalDate intakeDate) {
        this.intakeDate = intakeDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public Types.ExitCode getCode() {
        return code;
    }

    public void setCode(Types.ExitCode code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    // New getter and setter for locationID
    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String toString(){
        return "Animal{" +
                "animal_ID='" + id + '\'' +
                ", animal_Type='" + species + '\'' +
                ", animal_Name='" + name + '\'' +
                ", animal_weight=" + weight +
                ", intake_date=" + intakeDate +
                '}';
    }
}


