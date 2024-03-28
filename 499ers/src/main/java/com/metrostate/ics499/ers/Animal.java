package com.metrostate.ics499.ers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Animal {
    private static int idCounter = 1;
    private int id;
    private String name;
    private Types.SpeciesAvailable species;
    private double weight;
    private LocalDate DOB;
    private LocalDate intakeDate;
    private LocalDate exitDate;
    private Types.ExitCode code;
    private List<Record> records;

    public Animal(String name, Types.SpeciesAvailable species, double weight, LocalDate dob, LocalDate intakeDate) {
        this.id = idCounter++;
        this.name = name;
        this.species = species;
        this.weight = weight;
        this.DOB = dob;
        this.intakeDate = intakeDate;
        this.records = new ArrayList<>();
    }

    public Animal(int id, String name, Types.SpeciesAvailable species, double weight, LocalDate dob, LocalDate intakeDate) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.weight = weight;
        this.DOB = dob;
        this.intakeDate = intakeDate;
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

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String toString(){
        return "Animal{" +
                "animal_ID='" + id + '\'' +
                ", animal_Type='" + species + '\'' +
                ", animal_Name='" + name + '\'' +
                ", animal_weight=" + weight +
                ", intake_date=" + intakeDate +
                ", exit_date=" + exitDate +
                ", code=" + code +
                '}';
    }
}


