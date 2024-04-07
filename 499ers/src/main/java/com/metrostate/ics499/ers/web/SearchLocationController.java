package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

/**
 *  Controls the dynamic data insertion of the searchlocation.html webpage
 */
@Controller
public class SearchLocationController {

    /**
     * Maps /searchAnimal to the searchlocation.html web page, while also
     * providing the Form for searching to the page.
     *
     * @param model the form to be given to the search value
     * @return the mapping?
     */
    @RequestMapping(value = "/searchlocation", method = RequestMethod.GET)
    public String getSearchAnimal(Model model) {
        model.addAttribute("searchValue", new FormChoice());
        return "searchlocation";
    }

    /**
     * Posts to the searchlocation.html webpage the results of a given search.
     *
     * @param formData the information from the search bar form
     * @param searchValue the search bar from
     * @param model the list of locations to be dynamically added to the webpage
     * @return the mapping
     */
    @PostMapping("/searchforlocation")
    public String searchForValue(@RequestBody MultiValueMap<String, String> formData,
                                 @ModelAttribute("searchValue") FormChoice searchValue, Model model) {
        List<Location> results = searchLocation(formData);
        model.addAttribute("locations", results);
        return "searchlocation";
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
     * Returns a list of locations with the provided name in the database.
     *
     * @param values a map of search values, currently only contianing a name
     * @return a list of locations with a given name found in values
     */
    public List<Location> searchLocation (MultiValueMap values){
        String name = getName(values.get("name"));
        return DBAdapter.queryLocation("Location_Name", name);
    }
}