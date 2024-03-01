package com.metrostate.ics499.ers;

import java.util.Calendar;

public class Record {
    enum recordType{
        Medical,
        Behavioral,
        Other
    }
    private int id;
    private int employeeID;
    private Calendar updateDate;
    private recordType type;
    private String details;

    public Record(){

    }

    public void update(){

    }
}
