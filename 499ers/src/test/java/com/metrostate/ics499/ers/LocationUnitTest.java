package com.metrostate.ics499.ers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LocationUnitTest {

    String tName;
    String tAddress;
    int tCap;
    List<Types.SpeciesAvailable> tSpec;
    Location fosters;
    Location humane;

    @BeforeEach
    void init() {
        tName = "Foster's Home for Imaginary Friends";
        tAddress = "2024 Today Ln, 55122, MN";
        tCap = 1340;
        tSpec = new ArrayList<>();
        tSpec.add(Types.SpeciesAvailable.CAT);
        fosters = new Location(Types.LocType.FOSTER_HOME,tName, tAddress, tCap, tSpec);

        tName = "Humane Society of Testville";
        tAddress = "369 Adopt Ln, 55101, MN";
        tCap = 1;
        tSpec = new ArrayList<>();
        tSpec.add(Types.SpeciesAvailable.BIRD);
        tSpec.add(Types.SpeciesAvailable.CAT);
        tSpec.add(Types.SpeciesAvailable.DOG);
        tSpec.add(Types.SpeciesAvailable.RABBIT);
        humane = new Location(Types.LocType.FOSTER_HOME,tName, tAddress, tCap, tSpec);
    }

    @Test
    @DisplayName("Testing idCounter")
    void testIDCounter(){
        // First entry should have id of 1
        assertEquals(1, fosters.getId());
        // Second entry should have id of 2
        assertEquals(2, humane.getId());
    }

    @Test
    @DisplayName("Testing atCapacity")
    void testAtCapacity() {
        // atCapacity should return false if the location is not full
        assertFalse(humane.atCapacity());
        // atCapacity should return true if the location is full
        humane.setMaxCapacity(0);
        assertTrue(humane.atCapacity());
    }

    @Test
    @DisplayName("Testing addSpecies")
    void testAddSpecies(){
        // Adding a species not currently in the list should return true
        assertTrue(fosters.addSpecies(Types.SpeciesAvailable.RABBIT));
        // Adding a species currently in the list should return false
        assertFalse(humane.addSpecies(Types.SpeciesAvailable.RABBIT));
    }

    @Test
    @DisplayName("Testing removeSpecies")
    void testRemoveSpecies(){
        // Removing a species currently in the list should return true
        assertTrue(humane.removeSpecies(Types.SpeciesAvailable.CAT));
        // Removing a species not currently in the list should return false
        assertFalse(fosters.removeSpecies(Types.SpeciesAvailable.RABBIT));
    }

    @Test
    @DisplayName("Testing addAnimal")
    void testAddAnimal() {
        Calendar dob = Calendar.getInstance();
        dob.set(1963, Calendar.FEBRUARY, 14);
        Calendar intakeDate = Calendar.getInstance();
        Animal clifford = new Animal("Clifford the Big Red Dog", Types.SpeciesAvailable.DOG, "Red Lab",
                456.63, dob, intakeDate, "Testing a unit :)");

        // Adding a valid animal should return true; confirm addition
        assertTrue(humane.addAnimal(clifford));
        assertTrue(humane.getAnimals().contains(clifford));
        // Adding an invalid animal should return false; confirm no addition
        assertFalse(fosters.addAnimal(clifford));
        assertFalse(fosters.getAnimals().contains(clifford));
        // Adding an animal to a location at capacity should return false
        assertFalse(humane.addAnimal(clifford));
    }

    @Test
    @DisplayName("Testing removeAnimal")
    void testRemoveAnimal() {
        Calendar dob = Calendar.getInstance();
        dob.set(1963, Calendar.FEBRUARY, 14);
        Calendar intakeDate = Calendar.getInstance();
        Animal clifford = new Animal("Clifford the Big Red Dog", Types.SpeciesAvailable.DOG, "Red Lab",
                456.63, dob, intakeDate, "Testing a unit :)");

        // Removing an existing animal should return true; confirm removal
        humane.addAnimal(clifford);
        assertTrue(humane.removeAnimal(clifford));
        assertFalse(humane.getAnimals().contains(clifford));
        // Removing a non-existing animal should return false
        assertFalse(fosters.removeAnimal(clifford));
    }


}
