package com.metrostate.ics499.ers;

public class Person {
    enum designation{
        Adoptee,
        Employee,
        Foster
    }
    private int id;
    private String name;
    private String phone;
    private String address;
    private designation type;

    public Person(){

    }
}
