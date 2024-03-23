package com.metrostate.ics499.ers;

import java.time.LocalDate;

public class Record {
    private static int idCounter = 1;
    private int id;
    private int employeeID;
    private LocalDate updateDate;
    private Types.RecordType type;
    private String details;
    private boolean archived;
    private boolean isUrgent;

    public Record(int employeeID, LocalDate updateDate, Types.RecordType type, String details) {
        this.id = idCounter++;
        this.employeeID = employeeID;
        this.updateDate = updateDate;
        this.type = type;
        this.details = details;
    }

    // GETTERS AND SETTERS ############################################################################################
    // ################################################################################################################

    public int getId() {
        return id;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Types.RecordType getType() {
        return type;
    }

    public void setType(Types.RecordType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String newDetails) {
        this.details = newDetails;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isArchived() {
        return this.archived;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

}