package com.metrostate.ics499.ers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class Animal {
    private static int idCounter = 0;
    private int id;
    private String name;
    private Types.SpeciesAvailable species;
    private String breed;
    private double weight;
    private Calendar DOB;
    private Calendar intakeDate;
    private Calendar exitDate;
    private Types.ExitCode code;
    private String notes;
    private List<Record> records;

    // Logging and notification methods added here

    private void log(String message) {
        // Placeholder for actual logging mechanism
        System.out.println(message);
    }

    private void logRecordCreation(Record record) {
        String logMessage = "Record added for " + this.name + ": " + record.getDetails() + " [" + record.getType() + "]";
        log(logMessage);
    }

    private void sendNotification(String subject, String message) {
        // Placeholder method. Implement notification logic here.
        // EmailService.sendEmail("staff@example.com", subject, message);
        log("Notification sent: " + subject);
    }

    private void notifyUrgentRecordAdded(Record record) {
        String subject = "Urgent Record Added for " + this.name;
        String message = "An urgent record has been added: " + record.getDetails();
        sendNotification(subject, message);
    }

    private void notifyStatusChange(String newStatus) {
        String subject = "Status Change for " + this.name;
        String message = "The status has been updated to: " + newStatus;
        sendNotification(subject, message);
    }



    public Animal(String name, Types.SpeciesAvailable species, String breed, double weight, Calendar dob, Calendar intakeDate, String notes) {
        this.id = idCounter++;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.weight = weight;
        this.DOB = dob;
        this.intakeDate = intakeDate;
        this.notes = notes;
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        if (record != null) {
            this.records.add(record);
            // Additional logic can be added here if needed, such as logging or notifications
            // Log the record addition.
            logRecordCreation(record);

            // Notify if the record is urgent.
            if (record.isUrgent()) {
                notifyUrgentRecordAdded(record);
            }

            // Example: Update status and notify based on the record type.
            if (record.getType() == Types.RecordType.Medical && record.getDetails().contains("Severe")) {
                this.code = Types.ExitCode.MedicalHold; // Assuming this is a new status.
                notifyStatusChange("Medical Hold due to Severe Condition");
            }
        }
    }

    /**
     * Updates a specific record for this animal.
     * Finds the record by its ID and replaces it with the updated record.
     *
     * @param recordId The ID of the record to update.
     * @param updatedRecord The updated record to replace the old one.
     */
    public void updateRecord(int recordId, Record updatedRecord) {
        // Iterate through the list of records
        for (int i = 0; i < records.size(); i++) {
            Record currentRecord = records.get(i);
            // Check if the current record's ID matches the one we're looking to update
            if (currentRecord.getId() == recordId) {
                // Replace the old record with the updated one
                records.set(i, updatedRecord);
                return; // Exit the method once the record is found and updated
            }
        }
        // If no matching record is found, no action is performed
    }

    /**
     * Calculates the age of the animal in years.
     * @return Age of the animal in years.
     */
    public int calculateAge() {
        if (DOB == null) {
            return 0; // DOB not set
        }
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - DOB.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < DOB.get(Calendar.DAY_OF_YEAR)) {
            age--; // Adjust age if the animal hasn't had its birthday this year yet.
        }
        return age;
    }

    /**
     * Retrieves all records of a specified type for this animal.
     * @param type The type of records to retrieve (e.g., Medical, Behavioral).
     * @return A list of records matching the specified type.
     */
    public List<Record> getRecordsByType(Types.RecordType type) {
        return this.records.stream()
                .filter(record -> record.getType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the most recent records for the animal, limited by a specified count.
     * @param count The maximum number of recent records to retrieve.
     * @return A list of the most recent records, up to the specified count.
     */
    public List<Record> getRecentRecords(int count) {
        return this.records.stream()
                .sorted((record1, record2) -> record2.getUpdateDate().compareTo(record1.getUpdateDate())) // Assuming Record has getUpdateDate
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Determines if the animal is adoptable based on specific criteria.
     * Example criteria: The animal is not in transit and has a clean health record.
     *
     * @return true if the animal is adoptable, false otherwise.
     */
    public boolean isAdoptable() {
        // Check if the animal is currently in transit
        if (this.code == Types.ExitCode.inTransit) {
            return false; // Animal in transit is not adoptable
        }

        // Check if the animal has been at the shelter for a minimum amount of time
        // Assuming a minimum shelter time of 14 days for this example
        Calendar minShelterTime = Calendar.getInstance();
        minShelterTime.add(Calendar.DAY_OF_YEAR, -14); // Subtract 14 days from today
        if (this.intakeDate.after(minShelterTime)) {
            return false; // Animal has not been at the shelter long enough
        }

        // Check for severe behavioral issues
        for (Record record : records) {
            if (record.getType() == Types.RecordType.Behavioral && record.getDetails().contains("Severe")) {
                return false; // Animal with severe behavioral issues is not adoptable
            }
        }

        // Check the animal's health records for any issues
        for (Record record : records) {
            if (record.getType() == Types.RecordType.Medical && !record.getDetails().contains("Healthy")) {
                return false; // Animal with health issues is not adoptable
            }
        }

        // If none of the above conditions are met, the animal is considered adoptable
        return true;
    }

    /**
     * Archives a specific record by its ID.
     * If the record is found, its archived status is set to true.
     *
     * @param recordId The ID of the record to archive.
     * @return true if the record was found and archived; false otherwise.
     */
    public boolean archiveRecord(int recordId) {
        for (Record record : this.records) {
            if (record.getId() == recordId) {
                record.setArchived(true); // Assuming Record has a setArchived method.
                return true;
            }
        }
        return false;
    }

    /**
     * Batch updates records of a specified type with new details.
     *
     * @param type The type of records to update.
     * @param newDetails The new details to apply to each record of the specified type.
     * @return The number of records updated.
     */
    public int batchUpdateRecords(Types.RecordType type, String newDetails) {
        int updatedCount = 0;
        for (Record record : this.records) {
            if (record.getType() == type && !record.isArchived()) { // Ensure active records only
                record.setDetails(newDetails);
                updatedCount++;
            }
        }
        return updatedCount;
    }

    /**
     * Searches through the animal's records for records that contain the specified keyword
     * in their details. The search is case-insensitive.
     *
     * @param keyword The keyword to search for in the record details.
     * @return A list of records that contain the keyword.
     */
    public List<Record> searchRecords(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        return this.records.stream()
                .filter(record -> record.getDetails().toLowerCase().contains(lowerCaseKeyword))
                .collect(Collectors.toList());
    }


    /**
     * Marks the animal as adopted and updates relevant information.

    public void markAsAdopted() {
        this.code = Types.ExitCode.Adopt;
        updateAnimalStatusInDb();
    }

    /**
     * Marks the animal as dead and updates relevant information.

    public void markAsDeceased() {
        this.code = Types.ExitCode.Dead;
        updateAnimalStatusInDb();
    }

    /**
     * Marks the animal as a runaway.

    public void markAsRunaway() {
        this.code = Types.ExitCode.Runaway;
        updateAnimalStatusInDb();
    }

    /**
     * Marks the animal as in transit.

    public void markAsInTransit() {
        this.code = Types.ExitCode.inTransit;
        updateAnimalStatusInDb();
    }

    /**
     * Updates the animal's status in the database.
     * This method assumes the existence of a dbAdapter method that can handle the update.

    private void updateAnimalStatusInDb() {
        try {
            // Assuming a method in dbAdapter exists for updating an animal's code.
            dbAdapter.getInstance().update(this, "code", this.code.name());
        } catch (SQLException e) {
            // Handle exception or ignore if logging is not required.
            // For critical operations, consider rethrowing the exception or taking alternative actions.
        }
    }

    // The updateDatabase method
    private void updateDatabase() {
        try {
            dbAdapter.getInstance().update(this);
        } catch (SQLException e) {
            // Handle exception or log error
        }
    }

    public void save() {
        // Assuming dbAdapter has been correctly instantiated elsewhere
        try {
            dbAdapter.getInstance().insert(this);
        } catch (SQLException e) {
            // Handle exception
        }
    }


    public void delete() {
        try {
            dbAdapter.getInstance().delete(this);
        } catch (SQLException e) {
            // Handle exception or log error
        }
    }

    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    // Setter for 'name' with database update
    public void setName(String name) {
        this.name = name;
        //updateDatabase(); // Synchronize changes with the database
    }

    public Types.SpeciesAvailable getSpecies() {
        return species;
    }

    public void setSpecies(Types.SpeciesAvailable species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Sets the animal's weight. The weight must be positive.
     * @param weight The weight of the animal.
     */
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weight = weight;
        //updateDatabase();
    }


    public Calendar getDOB() {
        return DOB;
    }

    /**
     * Sets the animal's date of birth. The date must not be in the future.
     * @param DOB The date of birth of the animal.
     */
    public void setDOB(Calendar DOB) {
        Calendar today = Calendar.getInstance();
        if (!DOB.before(today)) {
            throw new IllegalArgumentException("Date of birth must not be in the future.");
        }
        this.DOB = DOB;
    }

    public Calendar getIntakeDate() {
        return intakeDate;
    }

    /**
     * Sets the intake date for the animal.
     * Ensures the date is neither in the future nor before the DOB.
     *
     * @param intakeDate The intake date to set.
     * @throws IllegalArgumentException if the date is invalid.
     */
    public void setIntakeDate(Calendar intakeDate) {
        Calendar today = Calendar.getInstance();
        if (intakeDate.after(today)) {
            throw new IllegalArgumentException("Intake date cannot be in the future.");
        }
        if (DOB != null && intakeDate.before(DOB)) {
            throw new IllegalArgumentException("Intake date cannot be before the DOB.");
        }
        this.intakeDate = intakeDate;
    }


    public Calendar getExitDate() {
        return exitDate;
    }

    public void setExitDate(Calendar exitDate) {
        this.exitDate = exitDate;
    }

    public Types.ExitCode getCode() {
        return code;
    }

    public void setCode(Types.ExitCode code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void removeRecord(Record record) {
        this.records.remove(record);
    }

    public void updateRecord(Record updatedRecord) {

    }

}
