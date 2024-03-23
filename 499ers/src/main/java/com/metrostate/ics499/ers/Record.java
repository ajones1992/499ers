package com.metrostate.ics499.ers;

import java.time.LocalDate;

public class Record {
    private static int idCounter = 1;
    private int id;
    private LocalDate updateDate;
    private Types.RecordType type;
    private String details;

    public Record(LocalDate updateDate, Types.RecordType type, String details) {
        this.id = idCounter++;
        this.updateDate = updateDate;
        this.type = type;
        this.details = details;
    }

    // GETTERS AND SETTERS ############################################################################################
    // ################################################################################################################

    public int getId() {
        return id;
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

}