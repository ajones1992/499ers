package com.metrostate.ics499.ers;

import java.sql.*;

/**
 * dbAdapter acts as an adapter from the Java/Interface side of the project to the
 * SQL database.
 */
public class DBAdapter {

    // singleton
    private static DBAdapter database;

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
     * Gets the instance of the dbAdapter.
     *
     * @return The instance of the dbAdapter
     */
    public static DBAdapter getInstance() {
        if (database == null) {
            database = new DBAdapter();
        }
        return database;
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
     * database with a new entry. (Note: This will be overloaded to include
     * arguments for an animal, Person, or Location)
     *
     * @param insertStatement the insert statement in sql
     * @throws SQLException throws when the SQL statement is malformed
     */
    public void insert(String insertStatement) throws SQLException {
        statement = connect.createStatement();
        statement.executeUpdate(insertStatement);
    }

    /**
     * Inserts the specified animal into the Animal table within the database.
     *
     * @param animal animal to be inserted
     * @throws SQLException throws when the SQL statement is malformed
     */
    public void insert (Animal animal) throws SQLException {
        // insert into Database.Tablename (i.e. Fragmenent.Player)
        // add each attribute of animal
        preparedStatement = connect
                .prepareStatement("insert into Animal values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, animal.getId());
        preparedStatement.setString(2, animal.getName());
        preparedStatement.setString(3, animal.getSpecies().toString());
        preparedStatement.setString(4, animal.getBreed());
        preparedStatement.setDouble(5, animal.getWeight());
        preparedStatement.setDate(6, (Date) animal.getDOB().getTime());
        preparedStatement.setDate(7, (Date) animal.getIntakeDate().getTime());
        preparedStatement.setDate(8, (Date) animal.getExitDate().getTime());
        preparedStatement.setString(9, animal.getCode().toString());
        preparedStatement.executeUpdate();

        // add each record of animal to record table
        preparedStatement =
                connect.prepareStatement("insert into Record values (?, ?)");
        for (Record r : animal.getRecords()) {
            preparedStatement.setInt(1, r.getId());
            preparedStatement.setInt(2, r.getEmployeeID());
            preparedStatement.setDate(3, (Date) r.getUpdateDate().getTime());
            preparedStatement.setString(4, r.getType().toString());
            preparedStatement.setString(5, r.getDetails());
            //preparedStatement.setInt(6, animal.getId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Inserts the specified person into the person table within the database.
     * May need to be separated into specific instance of Employees, Adoptees,
     * and fosters.
     *
     * @param person person to be inserted
     * @throws SQLException throws when the SQL statement is malformed
     */
    public void insert (Person person) throws SQLException {
        // insert into Database.Tablename (i.e. Fragmenent.Player)
        // add for each attribute for Person
        preparedStatement = connect
                .prepareStatement("insert into Person values (?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getPhone());
        preparedStatement.setString(4, person.getAddress());
        preparedStatement.setString(5, person.getType().toString());
        preparedStatement.executeUpdate();
    }

    /**
     * Inserts the specified Location into the person table within the database.
     * May need to be separated into specific instances of Shelters and Fosters
     *
     * @param location location to be inserted
     * @throws SQLException throws when the SQL statement is malformed
     */
    public void insert (Location location) throws SQLException {
        // insert into Database.Tablename (i.e. Fragmenent.Player)
        // add for each attribute for Location
        preparedStatement = connect
                .prepareStatement("insert into Location values (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, location.getType().toString());
        preparedStatement.setInt(2, location.getId());
        preparedStatement.setString(3, location.getName());
        preparedStatement.setString(4, location.getAddress());
        preparedStatement.setInt(5, location.getMaxCapacity());
        preparedStatement.executeUpdate();

        // next are lists and will have to be iterated through
        //preparedStatement = connect.prepareStatement("insert into **** values (?, ?)");
        // for every species in location's species
        // insert new species into table
        // for every animal in location's inventory
        // insert animal into table
    }

    /**
     * Uses a delete statement in the form of a string to delete a
     * specific or set of entries in the database. (Note: This will be
     * overloaded to include arguments for an animal, Person, or Location)
     *
     * @param DeleteStatement the delete statement in sql
     * @throws SQLException throws when the SQL statement is malformed
     */
    public void delete(String DeleteStatement) throws SQLException {
        statement = connect.createStatement();
        statement.executeUpdate(DeleteStatement);
    }

    /**
     * Deletes the specified animal from the Animal table in the database
     *
     * @param animal the animal to be deleted from the database
     * @throws SQLException throws when internal methods fail
     */
    public void delete(Animal animal) throws SQLException {
        statement = connect.createStatement();
        //statement.executeUpdate(String.format("Delete from Animal where id= %d ;", animal.getId()));
    }

    /**
     * Uses an update statement in the form of a string to update a
     * specific or set of entries in the database. (Note: This will be
     * overloaded to include arguments for an animal, Person, or Location)
     *
     * @param updateStatement the update statement in sql format
     * @throws SQLException throws when internal methods fail
     */
    public void update(String updateStatement) throws SQLException {
        statement = connect.createStatement();
        statement.executeUpdate(updateStatement);
    }


    /**
     * Updates the specified field for the provided animal in the database.
     *
     * @param animal the animal to be updated
     * @param field the field being updated
     * @param update the update to the field
     * @throws SQLException throws when internal method fails.
     */
    public void update(Animal animal, String field, String update) throws SQLException {
        statement = connect.createStatement();
        // statement.executeUpdate(
        // String.format("Update Animal set %s = %s where id = %d;",
        // field, update, animal.getId()));
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

}
