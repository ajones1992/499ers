package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UpdateAnimalController {

    // Handler to set up the form with a dynamic dropdown feature
    @RequestMapping(value = "/updateanimal", method = RequestMethod.GET)
    public String showUpdateAnimalForm(Model model) {

        model.addAttribute("aniChoice", new FormChoice());
        return "updateanimal";
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

    @PostMapping("/updateanimal")
    public String updateAnimal(@RequestBody MultiValueMap<String, String> formData, @ModelAttribute("aniChoice") FormChoice aniChoice, Model model) {

        // Extract the animal ID from the form data
        updateAnimal(formData, getAnimalID(aniChoice));

        return "redirect:dataaddedsuccess.html";
    }

    public int getAnimalID(FormChoice choice) {
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

    public double getWeight(Object obj){
        String inputWeight = removeBrackets(String.valueOf(obj));
        if(!inputWeight.equalsIgnoreCase(""))
            return Double.parseDouble(String.valueOf(inputWeight));
        return 0.0;
    }

    // Create Animal from data parsed from the submitted form
    public void updateAnimal(MultiValueMap values, int id){
        String name = getName(values.get("name"));
        double weight = getWeight(values.get("weight"));
        Animal oldAni = Application.getMasterList().getAnimal(id);

        if(name.equalsIgnoreCase("") || oldAni.getName().equalsIgnoreCase(name)){
            name = oldAni.getName();
        }
        if(Double.compare(weight, 0.0) == 0 || Double.compare(weight, oldAni.getWeight()) == 0){
            weight = oldAni.getWeight();
        }

        Application.getMasterList().getAnimal(id).update(new Animal(id, name, oldAni.getSpecies(), weight, oldAni.getDOB(), oldAni.getIntakeDate()));
    }
}
