package com.metrostate.ics499.ers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AnimalTransferController {

    @RequestMapping(value = "/selectanimaltransfer", method = RequestMethod.GET)
    public String selectanimaltransfer() {
        return "selectanimaltransfer";
    }
}
