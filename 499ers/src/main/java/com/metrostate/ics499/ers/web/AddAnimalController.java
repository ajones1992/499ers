package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class AddAnimalController {

    // Handler to display the form
    @GetMapping("/addanimal")
    public String showAddAnimalForm() {
        return "addanimal";
    }

    @PostMapping("/add")
    public String addAnimal(@RequestBody MultiValueMap<String, String> formData) {

        // Extract the location ID from the form data
        int locationId = Integer.parseInt(formData.getFirst("locationId"));

        // Use the Application class or your service layer to fetch the Location
        Location location = Application.getMasterList().getLocation(locationId);

        if (location != null) {
            // Create the Animal object from form data
            Animal animal = createAnimal(formData);
            // Add the Animal to the specified Location
            location.addAnimal(animal);
        }
        return "redirect:dataaddedsuccess.html";
    }

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

    public Animal createAnimal(MultiValueMap values){
        String name = getName(values.get("name"));
        Types.SpeciesAvailable type = getType(values.get("animal_type"));
        double weight = getWeight(values.get("weight"));
        LocalDate dob = getDOB(values.get("dob"));
        LocalDate entryDate = getEntryDate(values.get("datereceived"));

        return new Animal(name, type, weight, dob, entryDate);
    }
}
