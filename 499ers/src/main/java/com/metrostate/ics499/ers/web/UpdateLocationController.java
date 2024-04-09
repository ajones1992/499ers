package com.metrostate.ics499.ers.web;

import org.springframework.ui.Model;
import com.metrostate.ics499.ers.DBAdapter;
import com.metrostate.ics499.ers.Location;
import com.metrostate.ics499.ers.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UpdateLocationController {

    @Autowired
    private DBAdapter dbAdapter;

    @GetMapping("/updatelocation/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Location location = dbAdapter.getLocationById(id);
        if (location != null) {
            model.addAttribute("location", location);
            return "updatelocation";
        } else {
            // Handle "not found" case
            return "redirect:/locationNotFound";
        }
    }

    @PostMapping("/updatelocation")
    public String updateLocationAddress(@RequestParam("id") int id,
                                        @RequestParam("address") String newAddress,
                                        RedirectAttributes redirectAttributes) {
        Location location = dbAdapter.getLocationById(id);
        if (location != null) {
            location.setAddress(newAddress); // Update the address
            boolean isSuccess = dbAdapter.updateLocation(location);
            if (isSuccess) {
                redirectAttributes.addFlashAttribute("successMessage", "Location address updated successfully!");
                return "redirect:/displaylocations";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to update location address.");
                return "redirect:/error"; // Redirect to an error page
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Location not found.");
            return "redirect:/error"; // Redirect to an error page
        }
    }

    private Location createLocationFromForm(MultiValueMap<String, String> formData, int locationId) {
        Types.LocType type = getType(formData.getFirst("loc-type"));
        String name = getName(formData.getFirst("name"));
        String address = getAddress(formData.getFirst("address"));
        int capacity = getCapacity(formData.getFirst("capacity"));
        List<Types.SpeciesAvailable> list = getSpeciesHandled(formData);

        Location location = new Location(type, name, address, capacity, list);
        return location;
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

    public List<Types.SpeciesAvailable> getSpeciesHandled(MultiValueMap values) {
        List<Types.SpeciesAvailable> list = new ArrayList<>();
        if(values.get("dog") != null)
            list.add(Types.SpeciesAvailable.DOG);
        if(values.get("cat") != null)
            list.add(Types.SpeciesAvailable.CAT);
        if(values.get("bird") != null)
            list.add(Types.SpeciesAvailable.BIRD);
        if(values.get("rabbit") != null)
            list.add(Types.SpeciesAvailable.RABBIT);
        return list;
    }

    public boolean handlesDog(Object obj){
        return String.valueOf(obj).contains("DOG");
    }

    public boolean handlesCat(Object obj){
        return String.valueOf(obj).contains("CAT");
    }

    public boolean handlesBird(Object obj){
        return String.valueOf(obj).contains("BIRD");
    }

    public boolean handlesRabbit(Object obj){
        return String.valueOf(obj).contains("RABBIT");
    }
}