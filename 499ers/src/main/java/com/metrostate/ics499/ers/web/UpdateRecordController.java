package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import com.metrostate.ics499.ers.Record;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UpdateRecordController {

    // Handler to set up the form with a dynamic dropdown feature
    @RequestMapping(value = "/updaterecord", method = RequestMethod.GET)
    public String showUpdateAnimalForm(Model model) {

        model.addAttribute("recChoice", new FormChoice());
        return "updaterecord";
    }

    // This creates a model attribute that is a list of animal names
    // This is what the dropdown is populated with
    @ModelAttribute("recOptions")
    public List<FormSelection> getOptions() {
        FormSelection temp;
        ArrayList<FormSelection> options = new ArrayList<>();
        for (Record rec : Application.getMasterList().getAllRecords()) {
                temp = new FormSelection();
                temp.setChoice(rec.getId()+ ": " + rec.getDetails());
                temp.setChoiceID(rec.getId());
                options.add(temp);
        }
        return options;
    }

    @PostMapping("/updaterecord")
    public String updateRecord(@RequestBody MultiValueMap<String, String> formData, @ModelAttribute("aniChoice") FormChoice recChoice, Model model) {

        // Extract the animal ID from the form data
        updateRecord(formData, getRecordID(recChoice));

        return "redirect:dataaddedsuccess.html";
    }

    public int getRecordID(FormChoice choice) {
        return Integer.parseInt(choice.getChoice());
    }


    /*
     *Methods to parse submitted form data into usable objects/primitives.
     */

    public String getDetails(Object obj){
        return removeBrackets(String.valueOf(obj));
    }

    /*
     *Methods to parse submitted form data into usable objects/primitives.
     */
    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }

    // Create Animal from data parsed from the submitted form
    public void updateRecord(MultiValueMap values, int id){
        LocalDate today = LocalDate.now();
        String details = getDetails(values.get("textbox"));
        Types.RecordType type = Application.getMasterList().getRecord(id).getType();

        Application.getMasterList().getRecord(id).update(new Record(id, today, type, details));
    }
}
