package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddLocationController {

    @RequestMapping(value = "/addlocation", method = RequestMethod.GET)
    public String addLocation() {
        return "addlocation";
    }
}
