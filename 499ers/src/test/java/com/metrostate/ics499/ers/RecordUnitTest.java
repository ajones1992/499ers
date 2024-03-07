package com.metrostate.ics499.ers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RecordUnitTest {

    Calendar recordDate;
    Person bogey;
    Record test1;
    Record test2;
    Record test3;

    @BeforeEach
    void init() {
        recordDate = Calendar.getInstance();
        recordDate.set(1963, Calendar.FEBRUARY, 14);
        bogey = new Person(Types.designation.Adoptee);
        test1 = new Record(bogey.getId(), recordDate, Types.RecordType.Medical, "Testing Records ID 1");
        test2 = new Record(bogey.getId(), recordDate, Types.RecordType.Behavioral, "Testing Records ID 2");
        test3 = new Record(bogey.getId(), recordDate, Types.RecordType.Other, "Testing Records ID 3");

    }

    @Test
    @DisplayName("Testing idCounter")
    void testCounter() {
        // First entry should have id of 0
        assertEquals(0, test1.getId());

        // Second entry should have id of 1
        assertEquals(1, test2.getId());

        // Third entry should have id of 2
        assertEquals(2, test3.getId());
    }
}