package com.metrostate.ics499.ers;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecordUnitTest {

    LocalDate recordDate;
    Person bogey;
    Record test1;
    Record test2;
    Record test3;

    @BeforeEach
    void init() {
        recordDate = LocalDate.of(1963, 2, 14);
        bogey = new Person(Types.Designation.ADOPTEE);
        Animal furball = new Animal("Cat", Types.SpeciesAvailable.CAT, "calico", 9.6, LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 6), "new born kitten");
        test1 = new Record(bogey.getId(), furball.getId(), recordDate, Types.RecordType.MEDICAL, "Testing Records ID 1");
        test2 = new Record(bogey.getId(), furball.getId(),recordDate, Types.RecordType.BEHAVIORAL, "Testing Records ID 2");
        test3 = new Record(bogey.getId(), furball.getId(),recordDate, Types.RecordType.OTHER, "Testing Records ID 3");

    }

    @Test
    @Order(1)
    @DisplayName("Testing idCounter")
    void testCounter() {
        // First entry should have id of 1
        assertEquals(1, test1.getId());

        // Second entry should have id of 2
        assertEquals(2, test2.getId());

        // Third entry should have id of 3
        assertEquals(3, test3.getId());
    }
}