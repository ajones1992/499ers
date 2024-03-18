package com.metrostate.ics499.ers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DBInitializer implements CommandLineRunner {

    //Spring Boot will automagically wire this object using application.properties:
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        start(args);
    }

    public static void start(String[] args) {
        SpringApplication.run(DBInitializer.class, args);
    }

    public void reset() {
        jdbcTemplate.execute("DROP TABLE Animal");
        jdbcTemplate.execute("DROP TABLE Person");
        jdbcTemplate.execute("DROP TABLE Location");
        jdbcTemplate.execute("DROP TABLE Record");
        jdbcTemplate.execute("DROP TABLE Species_Available");
    }

    @Override
    public void run(String... args) throws Exception {
        //Create the database table:
        initDatabase();

        //Insert a record:
        fillDatabase();

        //Read records:
        List<Animal> animals = getAnimals();

        //Print read records:
        for (Animal a : animals) {
            System.out.println(a);
        }
        System.out.println("endProgram!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        reset();
    }

    public void initDatabase() throws FileNotFoundException {
        try {
            URL resource = this.getClass().getClassLoader().getResource("schema.sql");
            System.out.println();
            System.out.println(resource);
            System.out.println();
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
            System.out.println();
            System.out.println(resource);
            System.out.println();
            File schemaFile = Paths.get(resource.toURI()).toFile();
            Scanner reader = new Scanner(schemaFile);
            while (reader.hasNextLine()) {
                jdbcTemplate.execute(getSqlStatement(reader));
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new FileNotFoundException("Missing Resource File: insert.sql");
        }
    }

    public List<Animal> getAnimals() {
        String queryStatement = "SELECT * FROM animal";
        return jdbcTemplate.query(queryStatement, (resultSet, rowNum) -> extractAnimal(resultSet));
    }

    private Animal extractAnimal(ResultSet rs) throws SQLException {
        String name = rs.getString("Animal_Name");
        Types.SpeciesAvailable species = extractAnimalType(rs.getString("Animal_Type"));
        String breed = rs.getString("Breed");
        double weight = rs.getDouble("Weight");
        return new Animal(name, species, breed, weight, Calendar.getInstance(), Calendar.getInstance(), "No notes");
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
}