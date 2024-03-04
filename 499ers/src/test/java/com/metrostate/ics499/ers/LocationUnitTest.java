package com.metrostate.ics499.ers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationUnitTest {

    @Test
    void testCreateShelter() {
        List<Types.SpeciesAvailable> species = new ArrayList<>();
        Location testLoc = new Location(Types.LocType.Shelter,"", "", 0,species);

        assertTrue(testLoc.getType() == Types.LocType.Shelter);
    }

    @Test
    void testCreateFosterHome(){
        List<Types.SpeciesAvailable> species = new ArrayList<>();
        Location testLoc = new Location(Types.LocType.FosterHome,"", "", 0,species);

        assertTrue(testLoc.getType() == Types.LocType.FosterHome);

    }
}
