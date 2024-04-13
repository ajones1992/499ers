package com.metrostate.ics499.ers;

import java.time.LocalDate;

public class Record implements Updatable<Record> {
    private static int idCounter = 1;
    private int id;
    private LocalDate updateDate;
    private Types.RecordType type;
    private String details;

    public static void setStartid(int start) {
        idCounter = start;
    }

    public Record(LocalDate updateDate, Types.RecordType type, String details) {
        this.id = idCounter++;
        this.updateDate = updateDate;
        this.type = type;
        this.details = details;
    }

    public Record(int id, LocalDate updateDate, Types.RecordType type, String details) {
        this.id = id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String newDetails) {
        this.details = newDetails;
    }

    public String toString() {
        return String.format("Record [id = %d, updateDate = %s, type = %s, details = %s]",
                this.id, this.updateDate.toString(), this.type.toString(), this.details);
    }

    @Override
    public boolean update(Record update) {
        if (DBAdapter.update(this, update)) {
            this.setUpdateDate(update.getUpdateDate());
            this.setDetails(update.getDetails());
            return true;
        } else {
            return false;
        }
    }
}