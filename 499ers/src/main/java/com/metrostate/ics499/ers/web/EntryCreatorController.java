package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/newentry")
public class EntryCreatorController {

    public EntryCreatorController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public String getEntry() {
        return "newentry";
    }
}
