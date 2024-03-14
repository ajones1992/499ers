package com.metrostate.ics499.ers;

import org.junit.jupiter.api.*;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimalUnitTest {

    Calendar dob;
    Calendar intakeDate;
    Animal clifford;
    Animal garfield;
    Animal iago;
    Animal peterCottontail;

    @BeforeEach
    void init() {
        dob = Calendar.getInstance();
        dob.set(1963, Calendar.FEBRUARY, 14);
        intakeDate = Calendar.getInstance();
        clifford = new Animal("Clifford the Big Red Dog", Types.SpeciesAvailable.DOG, "Red Lab",
                456.63, dob, intakeDate, "Testing a unit :)");

        dob.set(1978, Calendar.JUNE, 19);
        intakeDate = Calendar.getInstance();
        garfield = new Animal("Garfield", Types.SpeciesAvailable.CAT, "Orange",
                12.1, dob, intakeDate, "Testing a unit :)");

        dob.set(1992, Calendar.NOVEMBER, 11);
        intakeDate = Calendar.getInstance();
        iago = new Animal("Iago", Types.SpeciesAvailable.BIRD, "Parrot",
                1.03, dob, intakeDate, "Testing a unit :)");

        dob.set(1971, Calendar.APRIL, 4);
        intakeDate = Calendar.getInstance();
        peterCottontail = new Animal("Peter Cottontail", Types.SpeciesAvailable.RABBIT, "Hare",
                3.11, dob, intakeDate, "Testing a unit :)");
    }
    @Test
    @Order(1)
    @DisplayName("Testing idCounter")
    void testCounter() {
        // First entry should have id of 1
        assertEquals(1, clifford.getId());
        // Second entry should have id of 2
        assertEquals(2, garfield.getId());
        // Third entry should have id of 3
        assertEquals(3, iago.getId());
        // Fourth entry should have id of 4
        assertEquals(4, peterCottontail.getId());
    }

    @Test
    @DisplayName("Testing addRecord")
    void testAddRecord() {
        Person bogey = new Person(Types.Designation.SHELTER_STAFF);
        Calendar recordDate = Calendar.getInstance();
        Record cliffordTestRecord = new Record(bogey.getId(), recordDate, Types.RecordType.MEDICAL,
                "Testing a new record :)");
        clifford.addRecord(cliffordTestRecord);

        assertTrue(clifford.getRecords().contains(cliffordTestRecord));
    }

    @Test
    @DisplayName("Testing removeRecord")
    void testRemoveRecord() {
        Person bogey = new Person(Types.Designation.SHELTER_STAFF);
        Calendar recordDate = Calendar.getInstance();
        Record cliffordTestRecord = new Record(bogey.getId(), recordDate, Types.RecordType.MEDICAL,
                "Testing a new record :)");
        clifford.addRecord(cliffordTestRecord);
        clifford.removeRecord(cliffordTestRecord);

        assertFalse(clifford.getRecords().contains(cliffordTestRecord));
    }

}