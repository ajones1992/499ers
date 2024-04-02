package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.DBAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.metrostate.ics499.ers.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/displayallanimals")
public class DisplayAllAnimalsController {


    @Autowired
    public DisplayAllAnimalsController(DBAdapter dbAdapter) {
    }

    @GetMapping
    public String getAllAnimals(Model model) {
        List<Animal> animals = DBAdapter.getAllAnimals();
        model.addAttribute("animals", animals);
        return "displayallanimals";
    }

}
