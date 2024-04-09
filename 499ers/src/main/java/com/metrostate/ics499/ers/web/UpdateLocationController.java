package com.metrostate.ics499.ers.web;

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

    @Autowired
    public UpdateLocationController(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    // Display a list of all locations
    @GetMapping("/updatelocation")
    public String listLocations(Model model) {
        model.addAttribute("locations", dbAdapter.getAllLocations());
        return "updatelocation"; // Ensure this matches the name of your HTML file for listing locations
    }

    // Show update form for a specific location
    @GetMapping("/updatelocation/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Location location = dbAdapter.getLocationById(id);
        if (location != null) {
            model.addAttribute("location", location);
            return "edit-location"; // Ensure this matches the name of your HTML form file
        }
        return "redirect:/updatelocation";
    }

    // Process the update form submission
    @PostMapping("/updatelocation/update/{id}")
    public String updateLocation(@PathVariable("id") int id,
                                 @ModelAttribute("location") Location location,
                                 RedirectAttributes redirectAttributes) {
        // Set the ID on the location object to ensure it matches the path variable
        location.setId(id);

        if (dbAdapter.updateLocation(location)) {
            redirectAttributes.addFlashAttribute("message", "Location updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update location.");
        }
        return "redirect:/updatelocation";
    }
}
