package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.DBAdapter;
import com.metrostate.ics499.ers.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/updatelocation2")
public class UpdateLocationCon2 {

    @Autowired
    public UpdateLocationCon2(DBAdapter dbAdapter) {
    }

    @GetMapping
    public String getAllLocations(Model model) {
        List<Location> locations = DBAdapter.getAllLocations();

        model.addAttribute("locations", locations);
        return "updatelocation2";
    }
}
