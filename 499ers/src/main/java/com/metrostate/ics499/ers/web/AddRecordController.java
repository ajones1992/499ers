package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import com.metrostate.ics499.ers.Record;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class AddRecordController {

    // Handler to set up the form with a dynamic dropdown feature
    @RequestMapping(value = "/addrecord", method = RequestMethod.GET)
    public String showAddRecordForm(Model model) {

        model.addAttribute("aniChoice", new FormChoice());
        return "addrecord";
    }

    // This creates a model attribute that is a list of location names
    // This is what the dropdown is populated with
    @ModelAttribute("aniOptions")
    public List<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (Animal ani : Application.getMasterList().getAllAnimals()) {
            options.add(ani.getName());
        }
        return options;
    }

    @PostMapping("/addrec")
    public String addRecord(@RequestBody MultiValueMap<String, String> formData, @ModelAttribute("aniChoice") FormChoice aniChoice, Model model) {
        System.out.println(String.valueOf(formData.get("record_type")));

        // Extract the location ID from the form data
        Animal ani = getAnimal(aniChoice);

        // Create the Animal object from form data
        Record rec = createRecord(formData);

        //Add the Animal to the specified Location
        ani.addRecord(rec);

        return "redirect:dataaddedsuccess.html";
    }

    public Animal getAnimal(FormChoice choice) {
        for (Animal ani: Application.getMasterList().getAllAnimals()) {
            if(ani.getName().equalsIgnoreCase(choice.getChoice())){
                return ani;
            }
        }
        return null;
    }

    public Types.RecordType getType(Object obj){
        String inputType = removeBrackets(String.valueOf(obj));
        return Types.RecordType.valueOf(inputType);
    }

    public String getDetails(Object obj){
        System.out.println(String.valueOf(obj));
        return removeBrackets(String.valueOf(obj));
    }

    /*
     *Methods to parse submitted form data into usable objects/primitives.
     */
    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }

    // Create Animal from data parsed from the submitted form
    public Record createRecord(MultiValueMap values){
        LocalDate date = LocalDate.now();
        Types.RecordType type = getType(values.get("record_type"));
        String details = getDetails(values.get("textbox"));
        return new Record(date, type, details);
    }
}
