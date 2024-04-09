package com.metrostate.ics499.ers.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.metrostate.ics499.ers.DBAdapter;
import com.metrostate.ics499.ers.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UpdateLocationController {

    private final DBAdapter dbAdapter;
    private static final Logger log = LoggerFactory.getLogger(UpdateLocationController.class);
    private Location editLocation;

    @Autowired
    public UpdateLocationController(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
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
                                 @ModelAttribute("location") Location location,
                                 RedirectAttributes redirectAttributes) {
        // Set the ID on the location object to ensure it matches the path variable
        location.setId(id);
        
        log.info("Updating location: {}", location);

        if (DBAdapter.update(editLocation, location)) {
            redirectAttributes.addFlashAttribute("message", "Location updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update location.");
        }
        return "redirect:/updatelocation";
    }
}
