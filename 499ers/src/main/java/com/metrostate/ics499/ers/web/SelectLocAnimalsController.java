package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/selectlocanimals")
public class SelectLocAnimalsController {

    public SelectLocAnimalsController() {
        // Constructor logic if necessary
    }

    @RequestMapping(method = RequestMethod.GET)
    public String selectLocationAnimals() {
        // This will return the name of the view (HTML or JSP file) to be rendered
        return "selectlocanimals";
    }
}
