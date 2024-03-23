package com.metrostate.ics499.ers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Order(1)
@SpringBootApplication
public class DBInitializer implements CommandLineRunner {

    //Spring Boot will automagically wire this object using application.properties:
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DBAdapter dbAccess;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(DBInitializer.class, args);
    }

    public void reset() {
        jdbcTemplate.execute("DROP TABLE Animal");
        jdbcTemplate.execute("DROP TABLE Person");
        jdbcTemplate.execute("DROP TABLE Location");
        jdbcTemplate.execute("DROP TABLE Record");
        jdbcTemplate.execute("DROP TABLE Species_Available");
        System.out.println("Tables dropped.");
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            reset();
        } catch (DataAccessException e) {
            // do not reset
        }

        //Create the database table:
        initDatabase();
        fillDatabase();

        //tests
        dbAccess = DBAdapter.getInstance(jdbcTemplate);
        testAnimalInsert();
        displayAnimals();
        testQueryAnimal();
        displayLocations();
        testInsertLocation();
        testQueryLocation();
        testUpdateLocation();
        displayLocations();

    }



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
        }
    }

    // methods that are for testing and may be removed ------------------------------------------------

    // test animal insert
    private void testAnimalInsert() {
        System.out.println("Test Animal Insert");
        Animal myAnimal = new Animal("George", Types.SpeciesAvailable.DOG, 100.5, LocalDate.now(), LocalDate.now());
        Location myLoc = dbAccess.queryLocation("Location_ID", "1").get(0);
        dbAccess.insert(myAnimal, myLoc);
    }

    // test animal Query
    private void testQueryAnimal() {
        System.out.println("Test Query Animal");
        System.out.println("Query for animal with id 102");
        ArrayList<Animal> results = new ArrayList<Animal>(
                dbAccess.queryAnimal("Animal_ID", "102"));
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
                dbAccess.queryLocation("Location_Name", "Foster Home #1"));
        System.out.println("Results: ");
        for (Location loc : results) {
            System.out.println(loc);
        }
    }

    // test insert on Location
    private void testInsertLocation() {
        System.out.println("Test Insert Location");
        Location myLoc = new Location(4, Types.LocType.SHELTER, "MyShelter", "500 Address St",
                30, new ArrayList<Types.SpeciesAvailable>());
        dbAccess.insert(myLoc);
    }

    private void testUpdateLocation() {
        System.out.println("Test Update Location");
        System.out.println("Update for Foster Home #1");
        ArrayList<Location> results = new ArrayList<Location>(
                dbAccess.queryLocation("Location_Name", "Foster Home #1"));
        if (!results.isEmpty()) {
            Location original = results.get(0);
            System.out.printf("Original: %d, %s, %s, %s, %d\n", original.getId(), original.getName(),
                    original.getType(), original.getAddress(), original.getMaxCapacity());
            Location modified = new Location(original.getId(), Types.LocType.SHELTER, "FH #1",
                    original.getAddress(), original.getMaxCapacity() + 5,
                    new ArrayList<Types.SpeciesAvailable>());
            System.out.printf("Changes: %d, %s, %s, %s, %d\n", modified.getId(), modified.getName(),
                    modified.getType(), modified.getAddress(), modified.getMaxCapacity());
            System.out.println("Execute update.");
            dbAccess.update(original, modified);
        }
        results = new ArrayList<Location>(
                dbAccess.queryLocation("Location_ID", "2"));
        if (!results.isEmpty()) {
            Location original = results.get(0);
            System.out.printf("Modified Original: %d, %s, %s, %s, %d\n", original.getId(), original.getName(),
                    original.getType(), original.getAddress(), original.getMaxCapacity());
        }
    }

    public List<Animal> getAnimals() {
        String queryStatement = "SELECT * FROM animal";
        return jdbcTemplate.query(queryStatement, (resultSet, rowNum) -> extractAnimal(resultSet));
    }

    private Animal extractAnimal(ResultSet rs) throws SQLException {
        int id = rs.getInt("Animal_ID");
        String name = rs.getString("Animal_Name");
        Types.SpeciesAvailable species = Types.SpeciesAvailable.valueOf(rs.getString("Animal_Type"));
        double weight = rs.getDouble("Weight");
        return new Animal(id, name, species, weight, LocalDate.now(), LocalDate.now());
    }


    private Types.SpeciesAvailable extractAnimalType(String type) {
        if (type.equalsIgnoreCase("CAT")) {
            return Types.SpeciesAvailable.CAT;
        } else if (type.equalsIgnoreCase("DOG")) {
            return Types.SpeciesAvailable.DOG;
        } else if (type.equalsIgnoreCase("RABBIT")) {
            return Types.SpeciesAvailable.RABBIT;
        } else {
            return Types.SpeciesAvailable.BIRD;
        }
    }

    private void displayAnimals() {
        //Read records:
        List<Animal> animals = getAnimals();

        //Print read records:
        for (Animal a : animals) {
            System.out.println(a);
        }
    }

    private void displayLocations() {
        //Read records:
        List<Location> locations = getLocations();

        //Print read records:
        for (Location l : locations) {
            System.out.println(l);
        }
    }

    public List<Location> getLocations() {
        String queryStatement = "SELECT * FROM Location";
        return jdbcTemplate.query(queryStatement, (resultSet, rowNum) -> extractLocation(resultSet));
    }

    private Location extractLocation(ResultSet rs) throws SQLException {
        int id = rs.getInt("Location_ID");
        Types.LocType type = Types.LocType.valueOf(rs.getString("Location_Type"));
        String name = rs.getString("Location_Name");
        String address = rs.getString("Address");
        int capacity = rs.getInt("Capacity");
        return new Location(id, type, name, address, capacity, new ArrayList<Types.SpeciesAvailable>());
    }


    private Types.LocType extractLocationType(String type) {
        if (type.equalsIgnoreCase("SHELTER")) {
            return Types.LocType.SHELTER;
        } else {
            return Types.LocType.FOSTER_HOME;
        }
    }
}