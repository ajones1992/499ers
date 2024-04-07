// CONTROLLER FILE FOR selectlocanimals.html
package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Application;
import com.metrostate.ics499.ers.Location;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SelectLocAnimalsController {

    public SelectLocAnimalsController() {
        // Constructor logic if necessary
    }

    // creates the form object as an attribute
    @RequestMapping(value = "/selectlocanimals", method = RequestMethod.GET)
    public String getSelLocAnimals(Model model) {
        model.addAttribute("locChoice", new FormChoice());
        return "selectlocanimals";
    }

    // This creates a model attribute that is a list of location names
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

    // This method displays animals based on the location selected
    @RequestMapping(value = "/selectLoc", method = RequestMethod.POST)
    public String formSub(@ModelAttribute("locChoice") FormChoice locChoice, Model model){
        for (Location loc_ : Application.getMasterList().getAllLocations()) {
            // Check if the names match for this location and the selected location
            if (loc_.getId() == Integer.parseInt(locChoice.getChoice())) {
                // Add the list of animals to be displayed as an attribute
                model.addAttribute("animals", loc_.getAnimals());
                return "selectlocanimals";
            }
        }
        return "redirect:/error.html";
    }
}