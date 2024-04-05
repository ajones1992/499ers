package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller

public class SearchAnimalResultsController {

    private static List<Animal> results;

    @RequestMapping(value = "/searchanimalresults", method = RequestMethod.GET)
    public String getSearchResults(Model model) {
        System.out.println("When does this happen");
        for (Animal a: results) { System.out.println(a); }
        model.addAttribute("animals", results);
        return "searchanimalresults";
    }

    public static void setResults(List<Animal> animals) {
        results = animals;
        for (Animal a: results) { System.out.println(a); }
    }
}
