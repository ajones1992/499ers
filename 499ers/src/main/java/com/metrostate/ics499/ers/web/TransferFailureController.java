package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TransferFailureController {

    @RequestMapping(value = "/animaltransferfailure", method = RequestMethod.GET)
    public String animaltransferfailure() {
        return "animaltransferfailure";
    }
}
