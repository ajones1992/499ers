package com.metrostate.ics499.ers.web;

import org.springframework.ui.Model;
import com.metrostate.ics499.ers.Animal;
import com.metrostate.ics499.ers.Types;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/displayallanimals")
public class DisplayAllAnimalsController {

    private static final List<Animal> animals = new ArrayList();
    static {
        String[] names = {"Mikleo","Echo","Jack","Solaire","Mia","Sophia"};
        for(int i=0; i < names.length; i++) {
            animals.add(new Animal(names[i], Types.SpeciesAvailable.CAT,
                    20 - i, LocalDate.of(2015 + i, 4 + i, 16 + i), LocalDate.now()));
        }
    }

    @GetMapping
    public String getAllAnimals(Model model) {
        model.addAttribute("animals", animals);
        return "displayallanimals";
    }

}
