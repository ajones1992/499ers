package com.metrostate.ics499.ers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnimalUnitTest {

    @Test
    void testAddRecord() {
        Calendar cdob = Calendar.getInstance();
        cdob.set(1963, Calendar.FEBRUARY, 14);
        Calendar intakeDate = Calendar.getInstance();
        Animal clifford = new Animal("Clifford the Big Red Dog",
                Types.SpeciesAvailable.Dog, "Red Lab", 456.63, cdob, intakeDate, "Testing a unit :)");
        Record cliffordTestRecord = new Record(9, intakeDate, Types.RecordType.Medical,
                "Testing a new record :)");
        clifford.addRecord(cliffordTestRecord);

        assertTrue(clifford.getRecords().contains(cliffordTestRecord));
    }

}