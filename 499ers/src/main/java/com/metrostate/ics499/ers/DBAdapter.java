package com.metrostate.ics499.ers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * dbAdapter acts as an adapter from the Java/Interface side of the project to the
 * SQL database.
 */
@Component
public class DBAdapter {

    // singleton
    private static DBAdapter databaseAccess;
    private static JdbcTemplate template;

    public static void initDBAdapter(JdbcTemplate temp) {
        if (template == null) template = temp;
    }

    /**
     * privately constructs and initializes the dbAdapter
     *
     */
    @Autowired
    public DBAdapter(JdbcTemplate template) {
        this.template = template;
    }

    public static DBAdapter getInstance(JdbcTemplate template) {
        if (databaseAccess == null) {
            databaseAccess = new DBAdapter(template);
        }
        return databaseAccess;
    }

    public static DBAdapter getInstance() {
        return databaseAccess;
    }

    /**
     * Inserts the specified Animal into the Animal table within the database.
     * Sets its foreign key based on the Location also provided. Returns true
     * if the operation was successful.
     *
     * @param animal animal to be inserted into the database
     * @param location location to which the animal will be linked
     * @return true if operation successful
     */
    public static boolean insert(Animal animal, Location location) {
        int id = animal.getId();
        String name = animal.getName();
        String type = animal.getSpecies() == null? null : animal.getSpecies().toString();
        double weight = animal.getWeight();
        LocalDate dob = animal.getDOB();
        LocalDate intake = animal.getIntakeDate();
        LocalDate exit = animal.getExitDate();
        String code = animal.getCode() == null? null : animal.getCode().toString();
        int locId = location.getId();
        String sqlStatement = String.format("INSERT INTO Animal " +
                        "VALUES (%d, '%s', '%s', %f, '%s', '%s', '%s', '%s', %d);",
                id, name, type, weight, dob, intake, exit, code, locId);
        template.execute(sqlStatement);
        return true;
    }

    /**
     * Inserts the specified species into the species available table, linking
     * the location to the species in the database. Returns true if the
     * operation was successful; false otherwise.
     *
     * @param species species to be linked to location
     * @param location location with the specified species available
     * @return true if operation successful; false otherwise.
     */
    public static boolean insert(Types.SpeciesAvailable species, Location location) {
        String sqlStatement = String.format("INSERT INTO Species_Available " +
                "VALUES (%d, '%s')", location.getId(), species.toString());
        try {
            template.execute(sqlStatement);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Inserts the specified Location into the Location table within the database.
     * Returns true if the operation was successful; false otherwise.
     *
     * @param location location to be inserted
     * @return true if operation successful; false otherwise
     */
    public static boolean insert(Location location) {
        int id = location.getId();
        String type = location.getType().toString();
        String name = location.getName();
        String address = location.getAddress();
        int max = location.getMaxCapacity();
        String sqlStatement = String.format("INSERT INTO Location " +
                        "VALUES (%d, '%s', '%s', '%s', %d);", id, name, type, address, max);
        try {
            template.execute(sqlStatement);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Inserts the specified record into the Record table, tied to the provided
     * animal. Returns true if operation successful; false otherwise.
     *
     * @param newRecord record to be inserted
     * @param animal the animal to which the record is attached
     * @return true if operation successful; false otherwise
     */
    public static boolean insert(Record newRecord, Animal animal) {
        int recID = newRecord.getId();
        int animalID = animal.getId();
        LocalDate updateDate = newRecord.getUpdateDate();
        Types.RecordType type = newRecord.getType();
        String details = newRecord.getDetails();
        String sqlStatement = String.format("INSERT INTO Record " +
                "VALUES (%d, %d, '%s', '%s', '%s');", recID, animalID,
                updateDate.toString(), type.toString(), details);
        try {
            template.execute(sqlStatement);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Updates a Location in the database to match the updated Location.
     *
     * @param oldLoc The old Location with no modified fields
     * @param updatedLoc The updated Location with modified fields
     * @return true if the operation was successful
     */
    public static boolean update(Location oldLoc, Location updatedLoc) {
        StringBuffer updateStatement = new StringBuffer("UPDATE Location SET ");
        if (!(oldLoc.getName().equals(updatedLoc.getName()))) {
            updateStatement.append(String.format("Location_Name = '%s',", updatedLoc.getName()));
        }
        if (!(oldLoc.getType().toString().equals(updatedLoc.getType().toString()))) {
            updateStatement.append(String.format("Location_Type = '%s',", updatedLoc.getType().toString()));
        }
        if (!(oldLoc.getAddress().equals(updatedLoc.getAddress()))) {
            updateStatement.append(String.format("Address = '%s',", updatedLoc.getAddress()));
        }
        if (oldLoc.getMaxCapacity() != updatedLoc.getMaxCapacity()) {
            updateStatement.append(String.format("Capacity = '%d',", updatedLoc.getMaxCapacity()));
        }
        updateStatement.deleteCharAt(updateStatement.length() - 1);
        updateStatement.append(String.format("WHERE Location_ID = %d;", oldLoc.getId()));
        template.execute(updateStatement.toString());
        return true;
    }

    /**
     * Returns a list of locations from the database based on the provided
     * search key and search term.
     *
     * @param searchKey the field being searched
     * @param query the search of the searchKey field
     * @return a list of locations that meet the defined query
     */
    public static List<Location> queryLocation(String searchKey, String query) {
        StringBuffer queryStatement = new StringBuffer("SELECT * FROM Location WHERE ");
        queryStatement.append(addLocationSearchKey(searchKey, query));
        return template.query(queryStatement.toString(), (resultSet, rowNum) -> extractLocation(resultSet));
    }

    /**
     * Verifies and adds the search key field of a location query. Returns a
     * StringBuffer with a SQL query segment with the searchKey field and
     * query.
     *
     * @param searchKey a field of location
     * @param query the query of the searchKey field
     * @return a StringBuffer with the SQL query segment for the
     * @throws IllegalArgumentException if the searchKey is not a field of Location
     */
    private static StringBuffer addLocationSearchKey(String searchKey, String query)
            throws IllegalArgumentException {
        StringBuffer statement = new StringBuffer();
        if (searchKey.equalsIgnoreCase("Location_ID")) {
            statement.append(String.format("Location_ID = '%d';", Integer.parseInt(query)));
        } else if (searchKey.equalsIgnoreCase("Location_Name")) {
            statement.append(String.format("Location_Name LIKE '%%%s%%';", query));
        } else if (searchKey.equalsIgnoreCase("Location_Type")) {
            statement.append(String.format("Location_Type = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Address")) {
            statement.append(String.format("Address LIKE '%%%s%%';", query));
        } else if (searchKey.equalsIgnoreCase("Capacity")) {
            statement.append(String.format("Capacity = '%d';", Integer.parseInt(query)));
        } else {
            throw new IllegalArgumentException("Invalid searchKey for Location");
        }
        return statement;
    }

    /**
     * Returns a Location extracted from a ResultSet.
     *
     * @param rs result set row from an SQL statement
     * @return Location in the result set
     * @throws SQLException throws when internal methods fail
     */
    private static Location extractLocation(ResultSet rs) throws SQLException {
        int id = rs.getInt("Location_ID");
        Types.LocType type = Types.LocType.valueOf(rs.getString("Location_Type"));
        String name = rs.getString("Location_Name");
        String address = rs.getString("Address");
        int capacity = rs.getInt("Capacity");
        Location location = new Location(id, type, name, address,
                capacity);
        List<Types.SpeciesAvailable> species = querySpeciesAvailable(location);
        location.setSpecies(species);
        List<Animal> animals = queryAnimal("Location_Id", Integer.toString(location.getId()));
        location.setAnimals(animals);
        return location;
    }

    /**
     * Returns a list of all locations in the Location table.
     *
     * @return list of all locations
     */
    public static List<Location> getAllLocations() {
        String queryStatement = "SELECT * FROM Location";
        return template.query(queryStatement, (resultSet, rowNum) -> extractLocation(resultSet));
    }

    /**
     * Returns a list of all the available species at a given location
     *
     * @param location location being check for available species.
     * @return list of available species at location
     */
    public static List<Types.SpeciesAvailable> querySpeciesAvailable(Location location) {
        String queryStatement = String.format("SELECT * FROM Species_Available " +
                "WHERE Location_ID = %d", location.getId());
        return template.query(queryStatement, (resultSet, rowNum) ->
                Types.SpeciesAvailable.valueOf(resultSet.getString("Species_Type")));
    }

    /**
     * Returns a list of records tied to a specific animal
     *
     * @param animal the animal to which the records belong
     * @return a list of records
     */
    public static List<Record> queryRecords(Animal animal) {
        String queryStatement = String.format("SELECT * FROM Record " +
                "WHERE Animal_ID = %d;", animal.getId());
        return template.query(queryStatement, (resultSet, rowNum) ->
                extractRecord(resultSet));
    }

    /**
     * Returns a record extracted from a ResultSet.
     *
     * @param rs Result set containing a Record
     * @return the record in the Result Set
     * @throws SQLException throws when internal methods fail
     */
    private static Record extractRecord(ResultSet rs) throws SQLException {
        int recID = rs.getInt("Record_ID");
        LocalDate updateDate = LocalDate.parse(rs.getString("Update_Date"));
        Types.RecordType type = Types.RecordType.valueOf(rs.getString("Record_Type"));
        String details = rs.getString("Details");
        return new Record(recID, updateDate, type, details);
    }

    /**
     * Returns a list of Animals from the database based on the provided
     * search key and search term.
     *
     * @param searchKey the field being searched
     * @param query the search of the searchKey field
     * @return a list of locations that meet the defined query
     */
    public static List<Animal> queryAnimal(String searchKey, String query) {
        StringBuffer queryStatement = new StringBuffer("SELECT * FROM Animal WHERE ");
        queryStatement.append(addAnimalSearchKey(searchKey, query));
        return template.query(queryStatement.toString(), (resultSet, rowNum) -> extractAnimal(resultSet));
    }

    /**
     * Verifies and adds the search key field of an animal query. Returns a
     * StringBuffer with a SQL query segment with the searchKey field and
     * query.
     *
     * @param searchKey a field of animal
     * @param query the query of the searchKey field
     * @return a StringBuffer with the SQL query segment for the
     * @throws IllegalArgumentException if the searchKey is not a field of Location
     */
    private static StringBuffer addAnimalSearchKey(String searchKey, String query)
            throws IllegalArgumentException {
        StringBuffer statement = new StringBuffer();
        if (searchKey.equalsIgnoreCase("Animal_ID")) {
            statement.append(String.format("Animal_ID = '%d';", Integer.parseInt(query)));
        } else if (searchKey.equalsIgnoreCase("Animal_Name")) {
            statement.append(String.format("Animal_Name LIKE '%%%s%%';", query));
        } else if (searchKey.equalsIgnoreCase("Animal_Type")) {
            statement.append(String.format("Animal_Type = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Weight")) {
            statement.append(String.format("Weight = '%f';", Double.parseDouble(query)));
        } else if (searchKey.equalsIgnoreCase("DOB")) {
            statement.append(String.format("DOB = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Received_Date"))  {
            statement.append(String.format("Received_Date = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Exit_Date"))  {
            statement.append(String.format("Exit_Date = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Exit_Code"))  {
            statement.append(String.format("Exit_Code = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Location_ID"))  {
            statement.append(String.format("Location_ID = '%d';", Integer.parseInt(query)));
        } else {
            throw new IllegalArgumentException("Invalid searchKey for Animal");
        }
        return statement;
    }

    /**
     * Returns an Animal extracted from a ResultSet.
     *
     * @param rs result set row from an SQL statement
     * @return Animal in the result set
     * @throws SQLException throws when internal methods fail
     */
    private static Animal extractAnimal(ResultSet rs) throws SQLException {
        int id = rs.getInt("Animal_ID");
        String name = rs.getString("Animal_Name");
        Types.SpeciesAvailable species = Types.SpeciesAvailable.valueOf(rs.getString("Animal_Type"));
        double weight = rs.getDouble("Weight");
        LocalDate dob = LocalDate.parse(rs.getString("DOB"));
        LocalDate intake = LocalDate.parse(rs.getString("Received_Date"));
        Animal animal = new Animal(id, name, species, weight, dob, intake);
        String eDate = rs.getString("Exit_Date");
        LocalDate exitDate = eDate != null && !eDate.equalsIgnoreCase("null")?
                LocalDate.parse(eDate): null;
        animal.setExitDate(exitDate);
        String eCode = rs.getString("Exit_Code");
        Types.ExitCode exitCode = eCode != null && !eCode.equalsIgnoreCase("null")?
                Types.ExitCode.valueOf(eCode): null;
        animal.setCode(exitCode);
        List<Record> records = queryRecords(animal);
        animal.setRecords(records);
        return animal;
    }

    /**
     * Returns a list of all animals in the Animal Table.
     *
     * @return a list of all animals
     */
    public static List<Animal> getAllAnimals() {
        String queryStatement = "SELECT * FROM animal";
        return template.query(queryStatement, (resultSet, rowNum) -> extractAnimal(resultSet));
    }

    // Method to update an animal in the database
    public static boolean updateAnimalEntry(Animal animal) throws SQLException {
        // Formulate the update statement using the animal's properties
        String updateStatement = String.format(
                "UPDATE Animal SET Name = ?, Species = ?, Weight = ?, DOB = ?, IntakeDate = ?, ExitDate = ?, ExitCode = ? WHERE Animal_ID = ?;",
                animal.getName(), animal.getSpecies().toString(), animal.getWeight(),
                animal.getDOB(), animal.getIntakeDate(), animal.getExitDate(), animal.getCode().toString(), animal.getId());

        // Execute the update statement in the database using jdbcTemplate
        template.update(updateStatement);
        return true;
    }

    public static boolean updateAnimal(Animal animal) {
        String sql = "UPDATE Animals SET name = ?, species = ?, weight = ?, DOB = ?, intakeDate = ?, exitDate = ?, code = ? WHERE id = ?";
        int result = template.update(sql,
                animal.getName(),
                animal.getSpecies().toString(),
                animal.getWeight(),
                animal.getDOB(),
                animal.getIntakeDate(),
                animal.getExitDate(),
                animal.getCode().toString(),
                animal.getId());

        return result > 0;
    }

    public static boolean updateAnimalAdoptionStatus(Animal animal) {
        String sql = "UPDATE Animals SET exitDate = ?, code = ? WHERE id = ?";
        int result = template.update(sql,
                animal.getExitDate(),
                animal.getCode().toString(),
                animal.getId());

        // Assuming you have a way to link the animal to an adopter, you might also update that relationship here

        return result > 0;
    }

    public static Location getLocationById(int locationId) {
        String sql = "SELECT * FROM Location WHERE id = ?";
        Location location = template.queryForObject(sql, new Object[]{locationId}, (rs, rowNum) ->
                new Location(
                        rs.getInt("id"),
                        Types.LocType.valueOf(rs.getString("type")),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("maxCapacity")
                ));

        // Assuming you have a method to fetch species for a given locationId
        List<Types.SpeciesAvailable> species = fetchSpeciesForLocation(locationId);
        location.setSpecies(species); // Update the location object with the fetched species list

        return location;
    }

    // Placeholder for the method to fetch species - you'll need to implement this based on your database schema
    private static List<Types.SpeciesAvailable> fetchSpeciesForLocation(int locationId) {
        // Implement the query to fetch species for the location
        // For example, query a junction table that links locations to species, if that's how your data is structured
        return new ArrayList<>(); // Return the actual list of species
    }


//    public boolean updateLocation(Location location) {
//        String sql = "UPDATE Location SET " +
//                "Location_Name = ?, " +
//                "Location_Type = ?, " +
//                "Address = ?, " +
//                "Capacity = ? " +
//                "WHERE Location_ID = ?";
//
//        try {
//            // Execute the update statement using JdbcTemplate
//            int rowsAffected = template.update(
//                    sql,
//                    location.getName(),
//                    location.getType().toString(),
//                    location.getAddress(),
//                    location.getMaxCapacity(),
//                    location.getId()
//            );
//            return rowsAffected > 0; // If one or more rows are affected, the update is successful
//        } catch (Exception e) {
//            // Log the exception and return false, indicating the update failed
//            // You can use a logging framework or simply print the stack trace
//            e.printStackTrace();
//            return false;
//        }
//    }

    public boolean updateLocation(Location location) {
        // Assuming you have a method that only updates the address field
        String sql = "UPDATE Location SET address = ? WHERE id = ?";
        int rowsAffected = template.update(sql, location.getAddress(), location.getId());
        return rowsAffected > 0;
    }


}
