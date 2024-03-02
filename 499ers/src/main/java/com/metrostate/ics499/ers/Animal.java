import java.util.Calendar;
import java.util.List;

enum exitCode{
    Dead,
    Runaway,
    Adopt
}

public class Animal {
    private int id;
    private String name;
    private speciesAvailable species;
    private String breed;
    private double weight;
    private Calendar DOB;
    private Calendar intakeDate;
    private Calendar exitDate;
    private exitCode code;
    private String notes;
    private List<Record> records;


    //constructor with parameters
    public Animal(int id, String name, SpeciesAvailable species, String breed, double weight, Calendar dob, Calendar intakeDate, String notes) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.weight = weight;
        this.DOB = dob;
        this.intakeDate = intakeDate;
        this.notes = notes;
        this.records = new ArrayList<>();
    }

    // Method to add a record to the animal's records list
    public void createRecord(Record record) {
        if (record != null) {
            this.records.add(record);
            // Additional logic can be added here if needed, such as logging or notifications
        }
    }

    // Method to update an existing record - implementation depends on the requirement
    public void updateRecord(Record updatedRecord) {
        if (updatedRecord != null) {
            for (int i = 0; i < records.size(); i++) {
                Record existingRecord = records.get(i);
                // Assuming Record has a unique ID or similar property to identify the correct one
                if (existingRecord.getId() == updatedRecord.getId()) {
                    records.set(i, updatedRecord);
                    // Additional logic can be added here if needed, such as logging or notifications
                    break;
                }
            }
        }
    }

    // Methods for animal entry management can be implemented here
    /**
     * Initializes an animal entry with default or initial values.
     * This method is assumed to be called right after a new Animal object is created.
     */
    public void createEntry() {
        // Check if the animal already has an intake date; if not, set it to the current date.
        if (this.intakeDate == null) {
            this.intakeDate = Calendar.getInstance();
        }

        // Initialize the records list if it hasn't been initialized.
        if (this.records == null) {
            this.records = new ArrayList<>();
        }

        // Create an initial record for this animal indicating its intake.
        // Assume Record constructor takes (id, employeeID, updateDate, type, details).
        // Here, you'll need to adjust according to your Record class constructor.
        Record intakeRecord = new Record(/* id */, /* employeeID */, this.intakeDate, Record.RecordType.OTHER, "Animal intake");
        this.records.add(intakeRecord);

        // Here you might also set other default values or perform initial setup tasks.

        // Optionally log the creation of this animal entry for auditing purposes.
        System.out.println("Created animal entry for: " + this.name);
    }

    /**
     * Transfers the animal to another shelter.
     *
     * @param newShelterId The ID of the shelter to which the animal is being transferred.
     */
    public void transferAnimal(String newShelterId) {
        // Update the animal's current shelter ID to the new shelter's ID.
        this.currentShelterId = newShelterId;

        // Update the exit date to mark when the animal left the previous shelter.
        this.exitDate = Calendar.getInstance();

        // Update the exit code to reflect that the animal was transferred.
        this.code = ExitCode.TRANSFERRED; // Assuming TRANSFERRED is a value in the ExitCode enum.

        // Create a record to log the transfer.
        Record transferRecord = new Record(/* id */, /* employeeID */, this.exitDate, Record.RecordType.OTHER, "Transferred to shelter ID: " + newShelterId);
        this.records.add(transferRecord);

        // Optionally, log the transfer for auditing purposes.
        System.out.println("Transferred animal ID " + this.id + " to shelter ID " + newShelterId);
    }

    /**
     * Updates the animal's details.
     *
     * @param name New name of the animal, or null if no change.
     * @param species New species of the animal, or null if no change.
     * @param breed New breed of the animal, or null if no change.
     * @param weight New weight of the animal, or -1 if no change.
     * @param DOB New date of birth of the animal, or null if no change.
     * @param intakeDate New intake date of the animal, or null if no change.
     * @param exitDate New exit date of the animal, or null if no change.
     * @param code New exit code of the animal, or null if no change.
     * @param notes New notes about the animal, or null if no change.
     */
    public void updateEntry(String name, SpeciesAvailable species, String breed, double weight,
                            Calendar DOB, Calendar intakeDate, Calendar exitDate,
                            ExitCode code, String notes) {
        if (name != null) this.name = name;
        if (species != null) this.species = species;
        if (breed != null) this.breed = breed;
        if (weight != -1) this.weight = weight;
        if (DOB != null) this.DOB = DOB;
        if (intakeDate != null) this.intakeDate = intakeDate;
        if (exitDate != null) this.exitDate = exitDate;
        if (code != null) this.code = code;
        if (notes != null) this.notes = notes;
    }


    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for species
    public SpeciesAvailable getSpecies() {
        return species;
    }

    public void setSpecies(SpeciesAvailable species) {
        this.species = species;
    }

    // Getter and setter for breed
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // Getter and setter for weight
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Getter and setter for DOB
    public Calendar getDOB() {
        return DOB;
    }

    public void setDOB(Calendar DOB) {
        this.DOB = DOB;
    }

    // Getter and setter for intakeDate
    public Calendar getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(Calendar intakeDate) {
        this.intakeDate = intakeDate;
    }

    // Getter and setter for exitDate
    public Calendar getExitDate() {
        return exitDate;
    }

    public void setExitDate(Calendar exitDate) {
        this.exitDate = exitDate;
    }

    // Getter and setter for exitCode
    public ExitCode getCode() {
        return code;
    }

    public void setCode(ExitCode code) {
        this.code = code;
    }

    // Getter and setter for notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    // Methods for manipulating records list
    public void addRecord(Record record) {
        if (record != null) {
            this.records.add(record);
        }
    }

    public void removeRecord(Record record) {
        this.records.remove(record);
    }

    // You might need to add a method to set or update the currentShelterId if it's being tracked.
    public void setCurrentShelterId(String shelterId) {
        this.currentShelterId = shelterId;
    }

    public String getCurrentShelterId() {
        return this.currentShelterId;
    }

}
