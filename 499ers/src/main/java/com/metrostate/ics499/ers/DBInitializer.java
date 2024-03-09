package com.metrostate.ics499.ers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;
import java.util.List;

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
        jdbcTemplate.execute("DROP TABLE animal");
    }

    @Override
    public void run(String... args) throws Exception {

        //Create the database table:
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS animal(name VARCHAR(100))");

        //Insert a record:
        jdbcTemplate.execute("INSERT INTO animal VALUES ('Stella')");

        //Read records:
        List<Animal> animals = jdbcTemplate.query("SELECT * FROM animal",
                (resultSet, rowNum) -> new Animal(resultSet.getString("name"),
                        Types.SpeciesAvailable.Dog, "Great Dane", 80.08,
                        Calendar.getInstance(), Calendar.getInstance(),
                        "no Notes"));

        //Print read records:
        for (Animal a : animals) {
            System.out.println(a.getName());
        }
        System.out.println("endProgram!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //reset();
    }
}