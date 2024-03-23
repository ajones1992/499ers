package com.metrostate.ics499.ers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestUI {

    private static final LocationList locationList = new LocationList();

    /**
     * Method controls flow of user input from console.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Please choose from the following options:\n" +
                    "1: Add additional location\n" +
                    "2: Add additional incoming animal\n" +
                    "3: Enable receiving animals\n" +
                    "4: Disable receiving animals\n" +
                    "5: See animals in a location\n" +
                    "6: See animals in ALL locations\n" +
                    "7: Show all locations\n");
            int userOption = scan.nextInt();
            scan.nextLine();
            userMenu(userOption);
        } while(true);
    }

    /**
     * Method directs program commands for user selection through switch case
     * @param userOption - (int) user selected value from main()
     */
    public static void userMenu(int userOption) {
        int selected;
        Scanner scan = new Scanner(System.in);

        switch (userOption) {
            case 1:
                Location loc = createNewLocation();
                if(loc != null) {
                    locationList.addLocation(loc);
                    System.out.println("New location has been added \n");
                }
                break;
            //validate shelter ID, if location exists create animal and add to existing location
            case 2:
                locationList.showLocations();
                System.out.println("Please select a shelter: ");
                selected = scan.nextInt();
                scan.nextLine();
                if(shelterSearch(selected)) {
                    Animal newAnimal = createNewAnimal();
                    if(newAnimal != null) {
                        addUserCreatedAnimal(newAnimal, selected);
                    }
                }
                break;

            //Check for existing location, if exists update location to accept incoming animals
            case 3:
                locationList.showLocations();
                System.out.println("Please select a shelter: ");
                selected = scan.nextInt();
                scan.nextLine();
                if(shelterSearch(selected)) {
                    changeReceiving(selected, true);
                }
                break;

            //Check for existing location, if exists update location to deny incoming animals
            case 4:
                locationList.showLocations();
                System.out.println("Please select a shelter: ");
                selected = scan.nextInt();
                scan.nextLine();
                if(shelterSearch(selected)) {
                    changeReceiving(selected, false);
                }
                break;

            //validate that location exists, print all animals contained in the location
            case 5:
                locationList.showLocations();
                System.out.println("Please select a shelter: ");
                selected = scan.nextInt();
                scan.nextLine();
                if(shelterSearch(selected)){
                    System.out.println(locationList.getLocation(selected).showAnimals());
                }
                break;

            //call method to display all animals to console by location
            case 6:
                showAllAnimals();
                break;

            case 7:
                locationList.showLocations();
        }
    }

    /**
     * Method responsible for verifying that a shelter exists and is accepting new Animal objects
     * @param selected - (String) User submitted String for shelter name search
     * @return - boolean representing if new Animal objects can be added to shelter
     */
    public static boolean shelterSearch(int selected){
        if (locationList.containsLocation(selected)) {
            if (!locationList.getLocation(selected).atCapacity()) {
                return true;
            } else {
                System.out.println("This shelter is not receiving animals\n");
                return false;
            }
        } else {
            System.out.println("Invalid shelter ID\n");
        }
        return false;
    }

    /**
     * Method to toggle isReceiving attribute of a Shelter object to receiving or not receiving based on
     * submitted shelter name and boolean value.
     * @param selected - (String) user submitted shelter name
     * @param status - (boolean) true enables receiving, false disables
     */
    public static void changeReceiving(int selected, boolean status) {
        if(status){
            System.out.println("Receiving enabled for shelter " + selected + "\n");
        } else {
            System.out.println("Receiving disabled for shelter " + selected + "\n");
        }
    }

    /**
     * Method responsible for collecting user input to create new Animal object
     * @return (Animal) - user defined animal object
     */
    public static Animal createNewAnimal() {
        LocalDate c = LocalDate.now();
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Please enter the animal species: ");
            String strType = scan.nextLine();
            Types.SpeciesAvailable species = Types.SpeciesAvailable.valueOf(strType);
            System.out.println("Please enter the animal name: ");
            String name = scan.nextLine();
            System.out.println("Please enter the animal breed: ");
            String breed = scan.nextLine();
            System.out.println("Please enter the animal weight: ");
            double weight = scan.nextDouble();
            scan.nextLine();
            return new Animal(name, species, weight, c, c);
        }catch (Exception e){
            System.out.println("Animal could not be created\n");
            return null;
        }
    }

    /**
     * Method responsible for collecting user input to create new Animal object
     * @return (Animal) - user defined animal object
     */
    public static Location createNewLocation() {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Please enter an location type: ");
            Types.LocType locType = Types.LocType.valueOf(scan.nextLine());
            System.out.println("Please enter an location name: ");
            String locName = scan.nextLine();
            System.out.println("Please enter an address: ");
            String locAddress = scan.nextLine();
            System.out.println("Please enter the location's max capacity: ");
            int locCap = scan.nextInt();
            scan.nextLine();
            List<Types.SpeciesAvailable> locSpecies = new ArrayList<Types.SpeciesAvailable>();
            System.out.println("Please enter the main animal type handled at this location: ");
            String str = scan.nextLine();
            locSpecies.add(Types.SpeciesAvailable.valueOf(str));
            return new Location(locType,locName,locAddress,locCap,locSpecies);
        }catch (Exception e){
            System.out.println("Location could not be created\n");
            return null;
        }
    }

    /**
     * Method responsible for adding Animal object into previously created Shelter object
     * @param newAnimal - (Animal) animal object to be added to shelter object
     * @param selected - (String) shelter ID
     */
    public static void addUserCreatedAnimal(Animal newAnimal, int selected){
        try{
            locationList.getLocation(selected).addAnimal(newAnimal);
            System.out.println("New Animal has been added.\n");
        } catch (Exception e) {
            System.out.println("Animal could not be added.\n");
        }
    }

    /**
     * Method loops through all shelters in map and all animal objects in each shelter object
     * and prints all to console.
     */
    public static void showAllAnimals(){
        List<Location> allLocations = new ArrayList<>(locationList.getAllLocations());
        for (Location currentLocation : allLocations) {
            System.out.println("Shelter ID: " + currentLocation);
            for (int j = 0; j < currentLocation.getAnimals().size(); j++) {
                System.out.println(currentLocation.getAnimals().get(j));
            }
            System.out.println();
        }
    }
}
