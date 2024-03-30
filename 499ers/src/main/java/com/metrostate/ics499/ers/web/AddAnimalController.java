package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller

public class AddAnimalController {

    @RequestMapping(value = "/addanimal", method = RequestMethod.GET)
    public String getAnimal() {
        return "addanimal";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String contactFormSubmission(@RequestBody MultiValueMap values){

        Application.getMasterList().getLocation(1).addAnimal(createAnimal(values));
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
