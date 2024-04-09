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
public class UpdateLocationCon2 {

    private final DBAdapter dbAdapter;

    private static final Logger log = LoggerFactory.getLogger(UpdateLocationCon2.class);

    @Autowired
    public UpdateLocationCon2(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    // Display a list of all locations
    @GetMapping("/updatelocation2")
    public String listLocations(Model model) {
        model.addAttribute("locations", dbAdapter.getAllLocations());
        return "updatelocation2";
    }

    // Show update form for a specific location
    @GetMapping("/updatelocation2/edit/{Location_ID}")
    public String showUpdateForm(@PathVariable("Location_ID") int id, Model model) {
        log.info("Showing update form for Location_ID: {}", id);
        Location location = dbAdapter.getLocationById(id);
        if (location != null) {
            model.addAttribute("location", location);
            return "edit-location";
        }
        return "redirect:/updatelocation2";
    }

    // Process the update form submission
    @PostMapping("/updatelocation2/update/{Location_ID}")
    public String updateLocation(@PathVariable("Location_ID") int id,
                                 @ModelAttribute("location") Location location,
                                 RedirectAttributes redirectAttributes) {
        // Set the ID on the location object to ensure it matches the path variable
        location.setId(id);

        log.info("Updating location: {}", location);

        if (dbAdapter.updateLocation(location)) {
            redirectAttributes.addFlashAttribute("message", "Location updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update location.");
        }
        return "redirect:/updatelocation2";
    }
}
