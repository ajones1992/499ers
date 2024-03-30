package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class AddAnimalController {

    @RequestMapping(value = "/addanimal", method = RequestMethod.GET)
    public String getAnimal() {
        return "addanimal";
    }

    @RequestMapping(path = "/dataaddedsuccess", method = RequestMethod.POST)
    public String contactFormSubmission(@ModelAttribute final Animal newAnimal){

        return "redirect:success.html";
    }
}
