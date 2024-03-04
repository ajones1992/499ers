package com.metrostate.ics499.ers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Animal {
    private static int idCounter = 0;
    private int id;
    private String name;
    private Types.SpeciesAvailable species;
    private String breed;
    private double weight;
    private Calendar DOB;
    private Calendar intakeDate;
    private Calendar exitDate;
    private Types.ExitCode code;
    private String notes;
    private List<Record> records;

    //constructor with parameters
    public Animal(String name, Types.SpeciesAvailable species, String breed, double weight, Calendar dob, Calendar intakeDate, String notes) {
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

    // Method to add a record to the animal's records list
    public void createRecord(Record record) {
        if (record != null) {
            this.records.add(record);
            // Additional logic can be added here if needed, such as logging or notifications
        }
    }

    // Method to update an existing record - implementation depends on the requirement
    public void updateRecord(Record updatedRecord) {
//        if (updatedRecord != null) {
//            for (int i = 0; i < records.size(); i++) {
//                Record existingRecord = records.get(i);
//                // Assuming Record has a unique ID or similar property to identify the correct one
//                if (existingRecord.getId() == updatedRecord.getId()) {
//                    records.set(i, updatedRecord);
//                    // Additional logic can be added here if needed, such as logging or notifications
//                    break;
//                }
//            }
//        }
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for species
    public Types.SpeciesAvailable getSpecies() {
        return species;
    }

    public void setSpecies(Types.SpeciesAvailable species) {
        this.species = species;
    }

    // Getter and setter for breed
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // Getter and setter for weight
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Getter and setter for DOB
    public Calendar getDOB() {
        return DOB;
    }

    public void setDOB(Calendar DOB) {
        this.DOB = DOB;
    }

    // Getter and setter for intakeDate
    public Calendar getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(Calendar intakeDate) {
        this.intakeDate = intakeDate;
    }

    // Getter and setter for exitDate
    public Calendar getExitDate() {
        return exitDate;
    }

    public void setExitDate(Calendar exitDate) {
        this.exitDate = exitDate;
    }

    // Getter and setter for exitCode
    public Types.ExitCode getCode() {
        return code;
    }

    public void setCode(Types.ExitCode code) {
        this.code = code;
    }

    // Getter and setter for notes
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


    // Methods for manipulating records list
    public void addRecord(Record record) {
        if (record != null) {
            this.records.add(record);
        }
    }

    public void removeRecord(Record record) {
        this.records.remove(record);
    }

}
