package com.metrostate.ics499.ers;

enum designation{
    Adoptee,
    Employee,
    Foster
}

public class Person {
    private int id;
    private String name;
    private String phone;
    private String address;
    private designation type;

    public Person(){

    }
}
