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

    // Parameterized constructor for initializing record fields
    public Record(int id, int employeeID, Calendar updateDate, RecordType type, String details) {
        this.id = id;
        this.employeeID = employeeID;
        this.updateDate = updateDate;
        this.type = type;
        this.details = details;
    }

    // Method to update record details
    public void update(recordType type, String details) {
        if (type != null) {
            this.type = type;
        }
        if (details != null && !details.isEmpty()) {
            this.details = details;
        }
        // Update the updateDate to the current date to reflect the update action
        this.updateDate = Calendar.getInstance();
    }

    // Methods for creating and updating staff entries can be implemented here
    /**
     * Creates a staff entry with specified details.
     * This method is a placeholder for actual staff-related record creation logic.
     * @param employeeID The ID of the employee creating this entry.
     * @param details The details of the staff action being logged.
     */
    public void createStaffEntry(int employeeID, String details) {
        // Assuming a new ID is generated or assigned elsewhere for each new record
        // For simplicity, we're not changing the existing record's ID here.

        this.employeeID = employeeID; // Update the employee ID for the staff entry.
        this.updateDate = Calendar.getInstance(); // Set the update date to the current time.
        this.type = RecordType.OTHER; // Assuming 'OTHER' is used for staff actions. Adjust as needed.
        this.details = details; // Set the details provided to the staff entry.

        // Here, you might include logic to save this record to a database or another storage system.

        // Optionally, log this creation for auditing or debugging purposes.
        System.out.println("Created staff entry: " + details);
    }

    /**
     * Updates details of an existing staff entry.
     *
     * @param newType The new type for the record, if updating; pass null if no change to type.
     * @param newDetails New details for the record; pass null if details are unchanged.
     */
    public void updateStaffEntry(RecordType newType, String newDetails) {
        // Check if a new type is provided and update accordingly.
        if (newType != null) {
            this.type = newType;
        }

        // Check if new details are provided and update accordingly.
        if (newDetails != null && !newDetails.trim().isEmpty()) {
            this.details = newDetails;
        }

        // Update the updateDate to the current date and time to reflect this update.
        this.updateDate = Calendar.getInstance();

        // Here, you might include logic to persist this update to a database or another storage system.

        // Optionally, log this update for auditing or debugging purposes.
        System.out.println("Updated staff entry: Type=" + this.type + ", Details=" + this.details);
    }



    // Getters and setters for accessing and modifying the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}