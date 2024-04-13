package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UpdateLocationController {

    // Handler to set up the form with a dynamic dropdown feature
    @RequestMapping(value = "/updatelocation", method = RequestMethod.GET)
    public String showUpdateLocationForm(Model model) {

        model.addAttribute("locChoice", new FormChoice());
        return "updatelocation";
    }

    // This creates a model attribute that is a list of animal names
    // This is what the dropdown is populated with
    @ModelAttribute("locOptions")
    public List<FormSelection> getOptions() {
        FormSelection temp;
        ArrayList<FormSelection> options = new ArrayList<>();
        for (Location loc : Application.getMasterList().getAllLocations()) {
            temp = new FormSelection();
            temp.setChoice(loc.getName());
            temp.setChoiceID(loc.getId());
            options.add(temp);
        }
        return options;
    }

    @PostMapping("/updatelocation")
    public String updateLocation(@RequestBody MultiValueMap<String, String> formData, @ModelAttribute("locChoice") FormChoice locChoice, Model model) {

        // Extract the animal ID from the form data
        updateLocation(formData, getLocationID(locChoice));

        return "redirect:dataaddedsuccess.html";
    }

    public int getLocationID(FormChoice choice) {
        return Integer.parseInt(choice.getChoice());
    }


    /*
     *Methods to parse submitted form data into usable objects/primitives.
     */
    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }

    public String getName(Object obj){
        return removeBrackets(String.valueOf(obj));
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

    // Create Animal from data parsed from the submitted form
    public void updateLocation(MultiValueMap values, int id){
        String name = getName(values.get("name"));
        String address = getAddress(values.get("address"));
        int capacity = getCapacity(values.get("capacity"));
        List<Types.SpeciesAvailable> list = getSpeciesHandled(values);

        Location oldLoc = Application.getMasterList().getLocation(id);

        if(name.equalsIgnoreCase("") || oldLoc.getName().equalsIgnoreCase(name)){
            name = oldLoc.getName();
        }
        if(address.equalsIgnoreCase("") || oldLoc.getAddress().equalsIgnoreCase(address)){
            address = oldLoc.getAddress();
        }
        if(capacity == 0 || oldLoc.getMaxCapacity() == capacity){
            capacity = oldLoc.getMaxCapacity();
        }
        Application.getMasterList().getLocation(id).update(new Location(oldLoc.getId(), oldLoc.getType(), name, address, capacity, list));
    }
}
