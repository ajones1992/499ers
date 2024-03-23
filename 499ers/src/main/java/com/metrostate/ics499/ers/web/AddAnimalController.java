package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/addanimal")
public class AddAnimalController {

    public AddAnimalController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAnimal() {
        return "addanimal";
    }
}
