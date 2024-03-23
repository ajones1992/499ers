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
        // Revisit this method later

    }

    // GETTERS AND SETTERS ############################################################################################
    // ################################################################################################################

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
