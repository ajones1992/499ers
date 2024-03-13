package com.metrostate.ics499.ers;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonUnitTest {

    Person test1;
    Person test2;
    Person test3;
    @BeforeEach
    void init() {
        test1 = new Person(Types.Designation.ADOPTEE);
        test2 = new Person(Types.Designation.FOSTER_STAFF);
        test3 = new Person(Types.Designation.SHELTER_STAFF);
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