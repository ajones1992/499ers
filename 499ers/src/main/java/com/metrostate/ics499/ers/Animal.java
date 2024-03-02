package com.metrostate.ics499.ers;

import java.util.Calendar;
import java.util.List;

enum exitCode{
    Dead,
    Runaway,
    Adopt
}

public class Animal {
    private int id;
    private String name;
    private speciesAvailable species;
    private String breed;
    private double weight;
    private Calendar DOB;
    private Calendar intakeDate;
    private Calendar exitDate;
    private exitCode code;
    private String notes;
    private List<Record> records;

    public Animal(){

    }

    public void createRecord(Record record){
        records.add(record);
    }

    public void updateRecord(){

    }
}
