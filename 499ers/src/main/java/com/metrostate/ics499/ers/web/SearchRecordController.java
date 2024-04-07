package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;

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

    /**
     * Maps /searchAnimal to the searchrecord.html web page, while also
     * providing the Form for searching to the page.
     *
     * @param model the form to be given to the search value
     * @return the mapping?
     */
    @RequestMapping(value = "/searchrecord", method = RequestMethod.GET)
    public String getSearchAnimal(Model model) {
        model.addAttribute("searchValue", new FormChoice());
        return "searchrecord";
    }

    /**
     * Posts to the searchrecord.html webpage the results of a given search.
     *
     * @param formData the information from the search bar form
     * @param searchValue the search bar from
     * @param model the list of animals to be dynamically added to the webpage
     * @return the mapping
     */
    @PostMapping("/searchforrecord")
    public String searchForValue(@RequestBody MultiValueMap<String, String> formData,
                                 @ModelAttribute("searchValue") FormChoice searchValue, Model model) {
        List<Record> results = searchRecord(formData);
        if (results == null)  return "redirect:/error.html";
        model.addAttribute("records", results);
        return "searchrecord";
    }

    /**
     * Returns the name entered into the search bar
     *
     * @param obj a String representing what the user entered
     * @return the animal name being searched for
     */
    public String getName(Object obj){
        return removeBrackets(String.valueOf(obj));
    }

    /**
     * Removes unnecessary brackets from a search value.
     *
     * @param str the search value
     * @return the search value without brackets
     */
    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }

    /**
     * Returns a list of records with the provided name in the database.
     *
     * @param values a map of search values, currently only contianing a name
     * @return a list of records attached to a given name found in values
     */
    public List<Record> searchRecord(MultiValueMap values) {
        String name = getName(values.get("name"));
        List<Animal> query = DBAdapter.queryAnimal("Animal_Name", name);
        return query.size() > 0? query.get(0).getRecords() : null;
    }
}