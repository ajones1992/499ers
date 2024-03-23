package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dataaddedsuccess")
public class DataAddedSuccessController {

    public DataAddedSuccessController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public String getSuccess() {
        return "dataaddedsuccess";
    }
}
