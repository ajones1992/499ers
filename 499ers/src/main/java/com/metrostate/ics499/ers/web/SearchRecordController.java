package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;

import java.util.ArrayList;
import java.util.List;

import com.metrostate.ics499.ers.Record;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

/**
 *  Controls the dynamic data insertion of the searchrecord.html webpage
 */
@Controller
public class SearchRecordController {

    @RequestMapping(value = "/searchrecord", method = RequestMethod.GET)
    public String getSelAnimalRecords(Model model) {

        model.addAttribute("aniChoice", new FormChoice());
        return "searchrecord";
    }

    // This creates a model attribute that is a list of animal names
    // This is what the dropdown is populated with
    @ModelAttribute("aniOptions")
    public List<FormSelection> getOptions() {
        FormSelection temp;
        ArrayList<FormSelection> options = new ArrayList<>();
        for (Animal ani : Application.getMasterList().getAllAnimals()) {
            temp = new FormSelection();
            temp.setChoice(ani.getName());
            temp.setChoiceID(ani.getId());
            options.add(temp);
        }
        return options;
    }

    // This method displays animals based on the location selected
    @RequestMapping(value = "/selectAni", method = RequestMethod.POST)
    public String formSub(@ModelAttribute("aniChoice") FormChoice locChoice, Model model){
        for (Animal ani : Application.getMasterList().getAllAnimals()) {
            // Check if the names match for this location and the selected location
            if (ani.getId() == Integer.parseInt(locChoice.getChoice())) {
                // Add the list of animals to be displayed as an attribute
                model.addAttribute("records", ani.getRecords());
                return "searchrecord";
            }
        }
        return "redirect:/error.html";
    }
}