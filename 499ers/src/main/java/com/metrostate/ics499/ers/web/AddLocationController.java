package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Types;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.metrostate.ics499.ers.Location;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/addlocation")
public class AddLocationController {

    private List<Location> locations = new ArrayList<>(); // Temporary storage for locations

//    @GetMapping
//    public String addLocationForm(Model model) {
//        model.addAttribute("location", new Location()); // Add an empty Location object to the model
//        return "addlocation"; // This should be the name of your form view
//    }
    @GetMapping
    public String addLocationForm(Model model) {
        model.addAttribute("location", new Location());
        return "addlocation";
    }


    @PostMapping("/addlocation")
    public String addLocationSubmit(@ModelAttribute Location location, HttpServletRequest request) {
        // Handle checkboxes for species separately
        String[] selectedSpecies = request.getParameterValues("species");
        if (selectedSpecies != null) {
            for (String speciesStr : selectedSpecies) {
                location.getSpecies().add(Types.SpeciesAvailable.valueOf(speciesStr));
            }
        }

        // Add the location to the database (skipped here for brevity)

        System.out.println(location); // Print the location to console for verification

        // Redirect to a success page or display success directly
        return "redirect:/dataaddedsuccess";
    }

}
