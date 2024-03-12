package com.metrostate.ics499.ers;

public class Person {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String phone;
    private String address;
    private Types.Designation type;

    public Person(Types.Designation type){
        this.id = idCounter++;
        this.type = type;
    }

    // GETTERS AND SETTERS ############################################################################################
    // ################################################################################################################

    public int getId() {
        return id;
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

    public Types.Designation getType() {
        return type;
    }

    public void setType(Types.Designation type) {
        this.type = type;
    }
}
