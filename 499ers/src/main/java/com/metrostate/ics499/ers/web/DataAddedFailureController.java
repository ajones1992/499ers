package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dataaddedfailure")
public class DataAddedFailureController {

    public DataAddedFailureController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public String getFailure() {
        return "dataaddedfailure";
    }
}
