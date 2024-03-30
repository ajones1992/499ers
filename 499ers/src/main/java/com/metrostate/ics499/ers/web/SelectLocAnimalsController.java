package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Application;
import com.metrostate.ics499.ers.DBAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.metrostate.ics499.ers.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(path = "/selectLoc", method = RequestMethod.POST)
    public String contactFormSubmission(Model model, String locID){
        List<Animal> animals = Application.getMasterList().getLocation(Integer.parseInt(locID)).getAnimals();
        model.addAttribute("animals", animals);
        return "selectlocanimals";
    }
}
