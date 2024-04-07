package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class AddAnimalController {

    // Handler to set up the form with a dynamic dropdown feature
    @RequestMapping(value = "/addanimal", method = RequestMethod.GET)
    public String showAddAnimalForm(Model model) {

        model.addAttribute("locChoice", new FormChoice());
        return "addanimal";
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

    @PostMapping("/add")
    public String addAnimal(@RequestBody MultiValueMap<String, String> formData, @ModelAttribute("locChoice") FormChoice locChoice, Model model) {

        // Extract the location ID from the form data
        Location loc = getLocation(locChoice);

        if (true ) {
            // Create the Animal object from form data
            Animal animal = createAnimal(formData);

            //Add the Animal to the specified Location
            loc.addAnimal(animal);
        }
        return "redirect:dataaddedsuccess.html";
    }

    public Location getLocation(FormChoice choice) {
        for (Location loc : Application.getMasterList().getAllLocations()) {
            // Check if the names match for this location and the selected location
            if (loc.getId() == Integer.parseInt(choice.getChoice())) {
                return loc;
            }
        }
        return null;
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

    public Types.SpeciesAvailable getType(Object obj){
        String inputType = removeBrackets(String.valueOf(obj));
        return Types.SpeciesAvailable.valueOf(inputType);
    }

    public double getWeight(Object obj){
        String inputWeight = removeBrackets(String.valueOf(obj));
        return Double.parseDouble(String.valueOf(inputWeight));
    }

    public LocalDate getDOB(Object obj){
        String inputDOB = removeBrackets(String.valueOf(obj));
        return LocalDate.parse(inputDOB);
    }

    public LocalDate getEntryDate(Object obj){
        String inputEntryDate = removeBrackets(String.valueOf(obj));
        return LocalDate.parse(inputEntryDate);
    }

    // Create Animal from data parsed from the submitted form
    public Animal createAnimal(MultiValueMap values){
        String name = getName(values.get("name"));
        Types.SpeciesAvailable type = getType(values.get("animal_type"));
        double weight = getWeight(values.get("weight"));
        LocalDate dob = getDOB(values.get("dob"));
        LocalDate entryDate = getEntryDate(values.get("datereceived"));

        return new Animal(name, type, weight, dob, entryDate);
    }
}
