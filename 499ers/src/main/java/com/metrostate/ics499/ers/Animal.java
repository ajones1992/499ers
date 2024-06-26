package com.metrostate.ics499.ers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Animal implements Updatable<Animal> {
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

    public static void setStartid(int start) {
        idCounter = start;
    }

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

    /**
     * Adds a Record to this Animal, while also updating the
     * Database to reflect the relation
     *
     * @param record record to be added
     */
    public void addRecord(Record record) {
        if (record != null) {
            this.records.add(record);
            DBAdapter.insert(record, this);
        }
    }


    public void removeRecord(Record record) {
        this.records.remove(record);
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public LocalDate getIntakeDate() {
        return intakeDate;
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
                ", DOB =" + DOB +
                ", intake_date=" + intakeDate +
                ", exit_date=" + exitDate +
                ", code=" + code +
                " }";
    }

    @Override
    public boolean update(Animal update) {
        if (DBAdapter.update(this, update)) {
            this.setId(update.getId());
            this.setName(update.getName());
            this.setWeight(update.getWeight());
            this.setExitDate(update.getExitDate());
            this.setCode(update.getCode());
            return true;
        } else {
            return false;
        }
    }
}


