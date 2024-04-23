package com.metrostate.ics499.ers.web;

import com.metrostate.ics499.ers.Application;
import com.metrostate.ics499.ers.Location;
import com.metrostate.ics499.ers.Types;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddLocationController {

    @RequestMapping(value = "/addlocation", method = RequestMethod.GET)
    public String addLocation() {
        return "addlocation";
    }

    @RequestMapping(path = "/addloc", method = RequestMethod.POST)
    public String contactFormSubmission(@RequestBody MultiValueMap values){
        Location loc = createLocation(values);
        Application.getMasterList().addLocation(loc);

        return "redirect:dataaddedsuccess.html";
    }

    private Location createLocation(MultiValueMap values) {
        Types.LocType type = getType(values.get("loc_type"));
        String name = getName(values.get("name"));
        String address = getAddress(values.get("address"));
        int capacity = getCapacity(values.get("capacity"));
        List<Types.SpeciesAvailable> list = getSpeciesHandled(values);

        return new Location(type, name, address, capacity, list);
    }

    public String removeBrackets(String str){
        return str.replace("[", "").replace("]", "");
    }


    public String getName(Object obj){
        return removeBrackets(String.valueOf(obj));
    }


    public Types.LocType getType(Object obj){
        if(String.valueOf(obj).contains("SHELTER"))
            return Types.LocType.SHELTER;
        return Types.LocType.FOSTER_HOME;
    }

    public String getAddress(Object obj){
        return removeBrackets(String.valueOf(obj));
    }

    public int getCapacity(Object obj){
        String inputWeight = removeBrackets(String.valueOf(obj));
        return Integer.parseInt(String.valueOf(inputWeight));
    }

    public List<Types.SpeciesAvailable> getSpeciesHandled(MultiValueMap values) {
        List<Types.SpeciesAvailable> list = new ArrayList<>();
        if(String.valueOf(values.get("dog")).contains("DOG"))
            list.add(Types.SpeciesAvailable.DOG);
        if(String.valueOf(values.get("cat")).contains("CAT"))
            list.add(Types.SpeciesAvailable.CAT);
        if(String.valueOf(values.get("bird")).contains("BIRD"))
            list.add(Types.SpeciesAvailable.BIRD);
        if(String.valueOf(values.get("rabbit")).contains("RABBIT"))
            list.add(Types.SpeciesAvailable.RABBIT);
        return list;
    }
}