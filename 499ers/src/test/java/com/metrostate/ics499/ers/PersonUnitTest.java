package com.metrostate.ics499.ers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PersonUnitTest {

    Person test1;
    Person test2;
    Person test3;
    @BeforeEach
    void init() {
        test1 = new Person(Types.designation.Adoptee);
        test2 = new Person(Types.designation.FosterStaff);
        test3 = new Person(Types.designation.ShelterStaff);
    }

    @Test
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