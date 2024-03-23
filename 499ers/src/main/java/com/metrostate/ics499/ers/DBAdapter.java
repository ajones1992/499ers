package com.metrostate.ics499.ers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * dbAdapter acts as an adapter from the Java/Interface side of the project to the
 * SQL database.
 */
public class DBAdapter {

    // singleton
    private static DBAdapter databaseAccess;
    private JdbcTemplate jdbcTemplate;

    //instance variables
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    /**
     * privately constructs and initializes the dbAdapter
     *
     */
    private DBAdapter() {
            connect();
    }

    /**
     * privately constructs and initializes the dbAdapter
     *
     */
    @Autowired
    public DBAdapter(JdbcTemplate template) {
        jdbcTemplate = template;
        databaseAccess = this;
    }

    public static DBAdapter getInstance(JdbcTemplate template) {
        if (databaseAccess == null) {
            databaseAccess = new DBAdapter(template);
        }
        return databaseAccess;
    }

    /**
     * Gets the instance of the dbAdapter.
     *
     * @return The instance of the dbAdapter
     */
    public static DBAdapter getInstance() {
        if (databaseAccess == null) {
            databaseAccess = new DBAdapter();
        }
        return databaseAccess;
    }

    /**
     * initializes and connects to the database with the specified host url
     *
     * @throws IllegalArgumentException when connection fails due to bad arguments
     */
    private void connect()
            throws IllegalArgumentException {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:shelterRDB.db");
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Closes the Connection to the database
     *
     * @throws SQLException throws when any close method executes and fails
     */
    public void close() throws SQLException {
        if (resultSet != null)  resultSet.close();
        if (statement != null)  statement.close();
        if (connect != null)    connect.close();
    }

    /**
     * Uses an insert statement in the form of a string to update the
     * database with a new entry. Returns true if the operation was
     * successful. (Note: This will be overloaded to include arguments
     * for an animal, Person, or Location)
     *
     * @param insertStatement the insert statement in sql
     * @return true if operation successful; false otherwise
     * @throws SQLException throws when the SQL statement is malformed
     */
    public boolean insert(String insertStatement) throws SQLException {
        jdbcTemplate.update(insertStatement);
        return true;
    }

    /**
     * Inserts the specified Animal into the Animal table within the database.
     * Sets its foreign key based on the Location also provided. Returns true
     * if the operation was successful.
     *
     * @param animal animal to be inserted into the database
     * @param location location to which the animal will be linked
     * @return true if operation successful; false otherwise
     */
    public boolean insert (Animal animal, Location location) {
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
                "VALUES (%d, '%s', '%s', %f, '%s', '%s', '%s', '%s', %d);", id, name, type, weight, dob, intake, exit, code, locId);
        jdbcTemplate.execute(sqlStatement);
        return true;
    }

    public boolean insert (Person person) throws SQLException {
        return false;
    }

    /**
     * Inserts the specified Location into the Location table within the database.
     * Returns true if the operation was successful.
     *
     * @param location location to be inserted
     * @return true if operation successful; false otherwise
     */
    public boolean insert(Location location) {
        int id = location.getId();
        String type = location.getType().toString();
        String name = location.getName();
        String address = location.getAddress();
        int max = location.getMaxCapacity();
        String sqlStatement = String.format("INSERT INTO Location " +
                        "VALUES (%d, '%s', '%s', '%s', %d);", id, name, type, address, max);
        jdbcTemplate.execute(sqlStatement);
        return true;
    }

    /**
     * Uses a delete statement in the form of a string to delete a
     * specific or set of entries in the database. Returns true if
     * the operation was successful. (Note: This will be overloaded
     * to include arguments for an animal, Person, or Location)
     *
     * @param DeleteStatement the delete statement in sql
     * @return true if operation successful; false otherwise
     * @throws SQLException throws when the SQL statement is malformed
     */
    public boolean delete(String DeleteStatement) throws SQLException {
        jdbcTemplate.execute(DeleteStatement);
        return true;
    }

    public boolean delete(Animal animal) throws SQLException {
        return false;
    }

    /**
     * Uses an update statement in the form of a string to update a
     * specific or set of entries in the database. (Note: This will be
     * overloaded to include arguments for an animal, Person, or Location)
     *
     * @param updateStatement the update statement in sql format
     * @return true if operation successful; false otherwise
     * @throws SQLException throws when internal methods fail
     */
    public boolean update(String updateStatement) throws SQLException {
        jdbcTemplate.execute(updateStatement);
        return true;
    }

    /**
     * Updates a Location in the database to match the updated Location.
     *
     * @param oldLoc The old Location with no modified fields
     * @param updatedLoc The updated Location with modified fields
     * @return true if the operation was successful
     */
    public boolean update(Location oldLoc, Location updatedLoc) {
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
        jdbcTemplate.execute(updateStatement.toString());
        return true;
    }

    /**
     * Uses a select statement in the form of a string to query the
     * database and return the results as a string. (Note: This needs
     * to be adjusted based on how information is queried)
     *
     * @param selectStatement the select statement in sql
     * @return A string of the results listed by row and column
     * @throws SQLException throws when internal methods fail
     */
    public String Query(String selectStatement) throws SQLException {
        StringBuffer result = new StringBuffer();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(selectStatement);
        int columnCount = resultSet.getMetaData().getColumnCount();
        for(int i = 0; i < columnCount; i++) {
            result.append("Column ").append(i).append("\t");
        }
        result.append("\n\n");
        while (resultSet.next()) {
            for (int i = 0; i < columnCount; i++) {
                result.append(resultSet.getObject(i));
            }
            result.append("/n");
        }
        return result.toString();
    }

    /**
     * Returns a list of locations from the database based on the provided
     * search key and search term.
     *
     * @param searchKey the field being searched
     * @param query the search of the searchKey field
     * @return a list of locations that meet the defined query
     */
    public List<Location> queryLocation(String searchKey, String query) {
        StringBuffer queryStatement = new StringBuffer("SELECT * FROM Location WHERE ");
        queryStatement.append(addLocationSearchKey(searchKey, query));
        return jdbcTemplate.query(queryStatement.toString(), (resultSet, rowNum) -> extractLocation(resultSet));
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
    private StringBuffer addLocationSearchKey(String searchKey, String query)
            throws IllegalArgumentException {
        StringBuffer statement = new StringBuffer();
        if (searchKey.equalsIgnoreCase("Location_ID")) {
            statement.append(String.format("Location_ID = '%d';", Integer.parseInt(query)));
        } else if (searchKey.equalsIgnoreCase("Location_Name")) {
            statement.append(String.format("Location_Name = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Location_Type")) {
            statement.append(String.format("Location_Type = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Address")) {
            statement.append(String.format("Address = '%s';", query));
        } else if (searchKey.equalsIgnoreCase("Capacity")) {
            statement.append(String.format("Capacity = '%d';", Integer.parseInt(query)));
        } else {
            throw new IllegalArgumentException("Invalid searchKey for Location");
        }
        return statement;
    }

    //Extracts location from a result set

    /**
     * Returns a Location extracted from a ResultSet.
     *
     * @param rs result set row from an SQL statement
     * @return Location in the result set
     * @throws SQLException throws when internal methods fail
     */
    private Location extractLocation(ResultSet rs) throws SQLException {
        Types.LocType type = null;
        try {
            type = Types.LocType.valueOf(rs.getString("Location_Type"));
        } catch (IllegalArgumentException e) {
            // Leave type as null
        }
        int id = rs.getInt("Location_ID");
        String name = rs.getString("Location_Name");
        String address = rs.getString("Address");
        int capacity = rs.getInt("Capacity");
        return new Location(id, type, name, address, capacity, new ArrayList<Types.SpeciesAvailable>());
    }

    /**
     * Returns a list of Animals from the database based on the provided
     * search key and search term.
     *
     * @param searchKey the field being searched
     * @param query the search of the searchKey field
     * @return a list of locations that meet the defined query
     */
    public List<Animal> queryAnimal(String searchKey, String query) {
        StringBuffer queryStatement = new StringBuffer("SELECT * FROM Animal WHERE ");
        queryStatement.append(addAnimalSearchKey(searchKey, query));
        return jdbcTemplate.query(queryStatement.toString(), (resultSet, rowNum) -> extractAnimal(resultSet));
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
    private StringBuffer addAnimalSearchKey(String searchKey, String query)
            throws IllegalArgumentException {
        StringBuffer statement = new StringBuffer();
        if (searchKey.equalsIgnoreCase("Animal_ID")) {
            statement.append(String.format("Animal_ID = '%d';", Integer.parseInt(query)));
        } else if (searchKey.equalsIgnoreCase("Animal_Name")) {
            statement.append(String.format("Animal_Name = '%s';", query));
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
    private Animal extractAnimal(ResultSet rs) throws SQLException {
        Types.SpeciesAvailable species = null;
        try {
            species = Types.SpeciesAvailable.valueOf(rs.getString("Animal_Type"));
        } catch (IllegalArgumentException e) {
            // Leave species as null
        }
        int id = rs.getInt("Animal_ID");
        String name = rs.getString("Animal_Name");
        double weight = rs.getDouble("Weight");
        LocalDate dob = LocalDate.parse(rs.getString("DOB"));
        LocalDate intake = LocalDate.parse(rs.getString("Received_Date"));
        return new Animal(id, name, species, weight, dob, intake);
    }

    // Method to update an animal in the database
    public boolean updateAnimalEntry(Animal animal) throws SQLException {
        // Formulate the update statement using the animal's properties
        String updateStatement = String.format(
                "UPDATE Animal SET Name = ?, Species = ?, Weight = ?, DOB = ?, IntakeDate = ?, ExitDate = ?, ExitCode = ? WHERE Animal_ID = ?;",
                animal.getName(), animal.getSpecies().toString(), animal.getWeight(),
                animal.getDOB(), animal.getIntakeDate(), animal.getExitDate(), animal.getCode().toString(), animal.getId());

        // Execute the update statement in the database using jdbcTemplate
        jdbcTemplate.update(updateStatement);
        return true;
    }

    public boolean updateAnimal(Animal animal) {
        String sql = "UPDATE Animals SET name = ?, species = ?, weight = ?, DOB = ?, intakeDate = ?, exitDate = ?, code = ? WHERE id = ?";
        int result = jdbcTemplate.update(sql,
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

    public boolean updateAnimalAdoptionStatus(Animal animal) {
        String sql = "UPDATE Animals SET exitDate = ?, code = ? WHERE id = ?";
        int result = jdbcTemplate.update(sql,
                animal.getExitDate(),
                animal.getCode().toString(),
                animal.getId());

        // Assuming you have a way to link the animal to an adopter, you might also update that relationship here

        return result > 0;
    }

    public Location getLocationById(int locationId) {
        String sql = "SELECT * FROM Location WHERE id = ?";
        Location location = jdbcTemplate.queryForObject(sql, new Object[]{locationId}, (rs, rowNum) ->
                new Location(
                        rs.getInt("id"),
                        Types.LocType.valueOf(rs.getString("type")),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("maxCapacity"),
                        new ArrayList<>() // Initially, provide an empty list for species
                        // You'll populate this list in the next step
                ));

        // Assuming you have a method to fetch species for a given locationId
        List<Types.SpeciesAvailable> species = fetchSpeciesForLocation(locationId);
        location.setSpecies(species); // Update the location object with the fetched species list

        return location;
    }

    // Placeholder for the method to fetch species - you'll need to implement this based on your database schema
    private List<Types.SpeciesAvailable> fetchSpeciesForLocation(int locationId) {
        // Implement the query to fetch species for the location
        // For example, query a junction table that links locations to species, if that's how your data is structured
        return new ArrayList<>(); // Return the actual list of species
    }


}
