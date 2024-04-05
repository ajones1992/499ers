package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SearchAnimalController {

    @RequestMapping(value = "/searchanimal", method = RequestMethod.GET)
    public String getAnimal() {
            return "searchanimal";
        }

    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public String contactFormSubmission(@RequestBody MultiValueMap values){
        SearchAnimalResultsController.setResults(searchAnimal(values));
        return "redirect:searchanimalresults.html";
    }

    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }

    public String getName(Object obj){
        return removeBrackets(String.valueOf(obj));
    }

    public List<Animal> searchAnimal(MultiValueMap values){
        String name = getName(values.get("name"));
        return DBAdapter.queryAnimal("Animal_Name", name);
    }
}
