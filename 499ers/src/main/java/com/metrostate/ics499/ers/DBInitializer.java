package com.metrostate.ics499.ers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Order(1)
@SpringBootApplication
public class DBInitializer implements CommandLineRunner {

    //Spring Boot will automagically wire this object using application.properties:
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(DBInitializer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        reset();

        //Create the database table:
        initDatabase();
        fillDatabase();

        //Initialize DBAdapter
        DBAdapter.initDBAdapter(jdbcTemplate);
        // runTests(); // add back in for testing (REMOVE FOR FINAL VERSION)

    }

    /**
     * Drops each table in the database to prep the table to be reset.
     */
    public void reset() {
        try {
            jdbcTemplate.execute("DROP TABLE Animal");
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            jdbcTemplate.execute("DROP TABLE Location");
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            jdbcTemplate.execute("DROP TABLE Record");
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            jdbcTemplate.execute("DROP TABLE Species_Available");
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Tables dropped.");
    }

    /**
     * Runs tests on Database access
     */
    private void runTests() {
        testInsertAnimal();
        testInsertAnimal2();
        displayAnimals();
        testQueryAnimal();
        displayLocations();
        testInsertLocation();
        testUpdateLocation();
        displayLocations();
        testQueryLocation();
        testInsertSpecies();
        displaySpeciesAvailable();
        testQuerySpecies();
        testInsertRecord();
        displayRecords();
        testQueryRecord();
    }

    /**
     * Initializes the databases with the tables from the "schema.sql" file.
     *
     * @throws FileNotFoundException if the "schema.sql" file is not found
     */
    public void initDatabase() throws FileNotFoundException {
        try {
            URL resource = this.getClass().getClassLoader().getResource("schema.sql");
            File schemaFile = Paths.get(resource.toURI()).toFile();
            Scanner reader = new Scanner(schemaFile);
            while (reader.hasNextLine()) {
                jdbcTemplate.execute(getSqlStatement(reader));
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new FileNotFoundException("Missing Resource File: schema.sql");
        }
    }

    public String getSqlStatement(Scanner reader) {
        StringBuffer statement = new StringBuffer();
        boolean endStatement = false;
        while(reader.hasNextLine() && !endStatement) {
            statement.append(reader.nextLine());
            endStatement = statement.toString().contains(";");
        }
        System.out.println(statement.toString());
        return statement.toString();
    }

    /**
     * Inserts the database with preliminary data from the "insert.sql" file
     * so that it can be tested.
     *
     * @throws FileNotFoundException if the "insert.sql" file cannot be found
     */
    public void fillDatabase() throws FileNotFoundException {
        try {
            URL resource = this.getClass().getClassLoader().getResource("insert.sql");
            File schemaFile = Paths.get(resource.toURI()).toFile();
            Scanner reader = new Scanner(schemaFile);
            while (reader.hasNextLine()) {
                jdbcTemplate.execute(getSqlStatement(reader));
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new FileNotFoundException("Missing Resource File: insert.sql");
        } catch (UncategorizedSQLException e) {
            System.out.println("Database already filled");
            System.out.println(e.getMessage());
        }
    }

    // methods that are for testing and may be removed ------------------------------------------------

    // test animal insert
    private void testInsertAnimal() {
        System.out.println("Test Animal Insert");
        Animal myAnimal = new Animal("George", Types.SpeciesAvailable.DOG, 100.5, LocalDate.now(), LocalDate.now());
        Location myLoc = DBAdapter.queryLocation("Location_ID", "101").get(0);
        DBAdapter.insert(myAnimal, myLoc);
    }

    private void testInsertAnimal2() {
        System.out.println("Test Animal Insert");
        Animal myAnimal = new Animal("George the 2nd", Types.SpeciesAvailable.DOG, 100.5, LocalDate.now(), LocalDate.now());
        Location myLoc = DBAdapter.queryLocation("Location_ID", "101").get(0);
        DBAdapter.insert(myAnimal, myLoc);
    }

    // test animal Query
    private void testQueryAnimal() {
        System.out.println("Test Query Animal");
        System.out.println("Query for animal with name George");
        ArrayList<Animal> results = new ArrayList<Animal>(
                DBAdapter.queryAnimal("Animal_Name", "George"));
        System.out.println("Results: ");
        for (Animal animal : results) {
            System.out.println(animal);
        }
    }

    // test Query with Location
    private void testQueryLocation() {
        System.out.println("Test Query Location");
        System.out.println("Query for Foster Home #1");
        ArrayList<Location> results = new ArrayList<Location>(
                DBAdapter.queryLocation("Location_Name", "Foster Home #1"));
        System.out.println("Results: ");
        for (Location loc : results) {
            System.out.println(loc);
        }
    }

    // test insert on Location
    private void testInsertLocation() {
        System.out.println("Test Insert Location");
        Location myLoc = new Location(104, Types.LocType.SHELTER, "MyShelter", "500 Address St",
                30);
        DBAdapter.insert(myLoc);
    }

    private void testUpdateLocation() {
        System.out.println("Test Update Location");
        System.out.println("Update for Foster Home #1");
        ArrayList<Location> results = new ArrayList<Location>(
                DBAdapter.queryLocation("Location_Name", "Foster Home #1"));
        if (!results.isEmpty()) {
            Location original = results.get(0);
            System.out.printf("Original: %d, %s, %s, %s, %d\n", original.getId(), original.getName(),
                    original.getType(), original.getAddress(), original.getMaxCapacity());
            Location modified = new Location(original.getId(), Types.LocType.SHELTER, "FH #1",
                    original.getAddress(), original.getMaxCapacity() + 5);
            System.out.printf("Changes: %d, %s, %s, %s, %d\n", modified.getId(), modified.getName(),
                    modified.getType(), modified.getAddress(), modified.getMaxCapacity());
            System.out.println("Execute update.");
            DBAdapter.update(original, modified);
        }
        results = new ArrayList<Location>(
                DBAdapter.queryLocation("Location_ID", "102"));
        if (!results.isEmpty()) {
            Location original = results.get(0);
            System.out.printf("Modified Original: %d, %s, %s, %s, %d\n", original.getId(), original.getName(),
                    original.getType(), original.getAddress(), original.getMaxCapacity());
        }
    }

    private void testInsertSpecies() {
        System.out.println("Test SpeciesAvailable Insert");
        Location myLoc = DBAdapter.queryLocation("Location_ID", "101").get(0);
        DBAdapter.insert(Types.SpeciesAvailable.RABBIT, myLoc);
    }

    private void testQuerySpecies(){
        System.out.println("Test SpeciesAvailable Query");

        Location myLoc = DBAdapter.queryLocation("Location_ID", "101").get(0);
        System.out.printf("Query for all species at %s\n", myLoc.getName());
        List<Types.SpeciesAvailable> species = DBAdapter.querySpeciesAvailable(myLoc);
        for (Types.SpeciesAvailable s: species) {
            System.out.println(s.toString());
        }
    }

    private void testInsertRecord() {
        System.out.println("Test insert Record");
        Record myRecord = new Record(106, LocalDate.now(),
                Types.RecordType.OTHER, "Inserted new Record");
        Animal animal = DBAdapter.queryAnimal("Animal_Name", "Max").get(0);
        DBAdapter.insert(myRecord, animal);
    }

    private void testQueryRecord() {
        System.out.println("Test Record Query");
        Animal animal = DBAdapter.queryAnimal("Animal_Name", "Max").get(0);
        System.out.printf("Query for all records for %s\n", animal.getName());
        List<Record> records = DBAdapter.queryRecords(animal);
        for (Record r: records) {
            System.out.println(r.toString());
        }
    }

    private void displayAnimals() {
        //Read records:
        List<Animal> animals = DBAdapter.getAllAnimals();

        //Print read records:
        for (Animal a : animals) {
            System.out.println(a);
        }
    }

    private void displayLocations() {
        //Read records:
        List<Location> locations = DBAdapter.getAllLocations();

        //Print read records:
        for (Location l : locations) {
            System.out.println(l);
        }
    }

    private void displaySpeciesAvailable() {
        String queryStatement = "SELECT * FROM Species_Available;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(queryStatement);
        results.first();
        System.out.println("LocID | Species");
        while (!results.isAfterLast()) {
            System.out.printf("(%d, %s)\n", results.getInt("Location_ID"),
                    results.getString("Species_Type"));
            results.next();
        }
    }

    private void displayRecords() {
        String queryStatement = "SELECT * FROM Record;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(queryStatement);
        results.first();
        System.out.println("rID | aID | Update Date | Type | Details");
        while (!results.isAfterLast()) {
            System.out.printf("(%d, %d, %s, %s, %s)\n",
                    results.getInt("Record_ID"),
                    results.getInt("Animal_ID"),
                    Objects.requireNonNull(results.getString("Update_Date")),
                    results.getString("Record_Type"),
                    results.getString("Details"));
            results.next();
        }
    }
}