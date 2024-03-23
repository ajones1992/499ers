package com.metrostate.ics499.ers;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimalUnitTest {

    LocalDate dob;
    LocalDate intakeDate;
    Animal clifford;
    Animal garfield;
    Animal iago;
    Animal peterCottontail;

    @BeforeEach
    void init() {
        dob = LocalDate.of(1963, 2, 14);
        intakeDate = LocalDate.now();
        clifford = new Animal("Clifford the Big Red Dog", Types.SpeciesAvailable.DOG,
                456.63, dob, intakeDate);

        dob.of(1978, 6, 19);
        garfield = new Animal("Garfield", Types.SpeciesAvailable.CAT,
                12.1, dob, intakeDate);

        dob.of(1992, 11, 11);
        iago = new Animal("Iago", Types.SpeciesAvailable.BIRD,
                1.03, dob, intakeDate);

        dob.of(1971, 4, 4);
        peterCottontail = new Animal("Peter Cottontail", Types.SpeciesAvailable.RABBIT,
                3.11, dob, intakeDate);
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
        LocalDate recordDate = LocalDate.now();
        Record cliffordTestRecord = new Record(recordDate, Types.RecordType.MEDICAL,
                "Testing a new record :)");
        clifford.addRecord(cliffordTestRecord);

        assertTrue(clifford.getRecords().contains(cliffordTestRecord));
    }

    @Test
    @DisplayName("Testing removeRecord")
    void testRemoveRecord() {
        Person bogey = new Person(Types.Designation.SHELTER_STAFF);
        LocalDate recordDate = LocalDate.now();
        Record cliffordTestRecord = new Record(recordDate, Types.RecordType.MEDICAL,
                "Testing a new record :)");
        clifford.addRecord(cliffordTestRecord);
        clifford.removeRecord(cliffordTestRecord);

        assertFalse(clifford.getRecords().contains(cliffordTestRecord));
    }

}