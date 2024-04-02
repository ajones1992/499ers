package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Application;
import com.metrostate.ics499.ers.Location;
import org.springframework.ui.Model;
import com.metrostate.ics499.ers.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SelectLocAnimalsController {

    public SelectLocAnimalsController() {
        // Constructor logic if necessary
    }

    @RequestMapping(value = "/selectlocanimals", method = RequestMethod.GET)
    public String getSelLocAnimals() {
        return "selectlocanimals";
    }

    @ModelAttribute("locOptions")
    public List<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (Location loc : Application.getMasterList().getAllLocations()) {
            options.add(loc.getName());
        }
        return options;
    }

    @RequestMapping(path = "/selectLoc", method = RequestMethod.POST)
    public String formSub(String locID, Model model){
        for (Location loc : Application.getMasterList().getAllLocations()) {
            if (loc.getName().equalsIgnoreCase(locID)) {
                List<Animal> animals = Application.getMasterList().getLocation(loc.getId()).getAnimals();
                model.addAttribute("animals", animals);
                return "selectlocanimals";
            }
        }
        return "redirect:/error.html";
    }
}