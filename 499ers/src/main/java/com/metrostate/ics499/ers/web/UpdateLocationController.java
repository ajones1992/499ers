package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Application;
import com.metrostate.ics499.ers.Location;
import com.metrostate.ics499.ers.Types;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UpdateLocationController {

    // Display the update form with current location details
    @GetMapping("/updatelocation/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Location location = Application.getMasterList().getLocation(id);
        if (location != null) {
            model.addAttribute("location", location);
            return "updatelocation";
        } else {
            // handle location not found
            return "error";
        }
    }

    // Process the form submission
    @PostMapping("/updateloc")
    public String updateLocation(@RequestParam MultiValueMap<String, String> formData, RedirectAttributes redirectAttributes) {
        int locationId = Integer.parseInt(formData.getFirst("locationId"));
        Location existingLocation = Application.getMasterList().getLocation(locationId);

        if (existingLocation != null) {
            // Directly update the existing Location object
            existingLocation.setName(formData.getFirst("name"));
            existingLocation.setAddress(formData.getFirst("address"));
            existingLocation.setMaxCapacity(Integer.parseInt(formData.getFirst("capacity")));
            // Assuming you adjust Location class to allow setting type, or you skip updating type if it's immutable
            // existingLocation.setType(getType(formData.getFirst("loc_type")));
            existingLocation.setSpecies(getSpeciesHandled(formData)); // Adjust the Location class to support this if needed

            // If using a service/repository, save the updated Location to the database here

            redirectAttributes.addFlashAttribute("successMessage", "Location updated successfully!");
            return "redirect:/displaylocations";
        } else {
            // Handle location not found
            return "error";
        }
    }


    // Helper methods
    private Types.LocType getType(String typeString) {
        return Types.LocType.valueOf(typeString.toUpperCase());
    }

    private List<Types.SpeciesAvailable> getSpeciesHandled(MultiValueMap<String, String> formData) {
        List<Types.SpeciesAvailable> speciesList = new ArrayList<>();
        if (formData.containsKey("dog")) speciesList.add(Types.SpeciesAvailable.DOG);
        if (formData.containsKey("cat")) speciesList.add(Types.SpeciesAvailable.CAT);
        if (formData.containsKey("bird")) speciesList.add(Types.SpeciesAvailable.BIRD);
        if (formData.containsKey("rabbit")) speciesList.add(Types.SpeciesAvailable.RABBIT);
        return speciesList;
    }
}
