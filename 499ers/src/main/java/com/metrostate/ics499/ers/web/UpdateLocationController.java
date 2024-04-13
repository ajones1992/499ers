package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.metrostate.ics499.ers.DBAdapter;
import com.metrostate.ics499.ers.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UpdateLocationController {

    private final DBAdapter dbAdapter;
    private static final Logger log = LoggerFactory.getLogger(UpdateLocationController.class);
    private Location editLocation;

    @Autowired
    public UpdateLocationController(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
        System.out.println("Update Controller autowired dbAdapter");
    }

    // Display a list of all locations
    @GetMapping("/updatelocation")
    public String listLocations(Model model) {
        model.addAttribute("locations", dbAdapter.getAllLocations());
        return "updatelocation";
    }

    // Show update form for a specific location
    @GetMapping("/updatelocation/edit/{Location_ID}")
    public String showUpdateForm(@PathVariable("Location_ID") int id, Model model) {
        log.info("Showing update form for Location_ID: {}", id);
        editLocation = dbAdapter.getLocationById(id);
        if (editLocation != null) {
            model.addAttribute("location", editLocation);
            return "edit-location";
        }
        return "redirect:/updatelocation";
    }

    // Process the update form submission
    @PostMapping("/updatelocation/update/{Location_ID}")
    public String updateLocation(@PathVariable("Location_ID") int id,
                                 @RequestBody MultiValueMap values,
                                 RedirectAttributes redirectAttributes) {
        // Set the ID on the location object to ensure it matches the path variable
        Location location = createLocation(values, id);
        location.setSpecies(editLocation.getSpecies());
        location.setAnimals(editLocation.getAnimals());

        log.info("Updating location: {}", location);

        if (editLocation.update(location)) {
            redirectAttributes.addFlashAttribute("message", "Location updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update location.");
        }
        return "redirect:/updatelocation";
    }

    private Location createLocation(MultiValueMap values, int id) {
        Types.LocType type = getType(values.get("type"));
        String name = getName(values.get("name"));
        String address = getAddress(values.get("address"));
        int capacity = getCapacity(values.get("maxCapacity"));
        return new Location(id, type, name, address, capacity, null);
    }

    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }

    public String getName(Object obj){
        return removeBrackets(String.valueOf(obj));
    }


    public Types.LocType getType(Object obj){
        if(String.valueOf(obj).contains("SHELTER"))
            return Types.LocType.SHELTER;
        return Types.LocType.FOSTER_HOME;
    }

    public String getAddress(Object obj){
        return removeBrackets(String.valueOf(obj));
    }

    public int getCapacity(Object obj){
        String inputWeight = removeBrackets(String.valueOf(obj));
        return Integer.parseInt(String.valueOf(inputWeight));
    }

}
