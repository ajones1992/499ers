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

    public Person(int id, designation type){

        this.id = id;
        this.type = type;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public designation getType() {
        return type;
    }

    public void setType(designation type) {
        this.type = type;
    }
}
