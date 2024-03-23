package com.metrostate.ics499.ers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;


@Order(2)
@SpringBootApplication
public class Application implements CommandLineRunner {
	private static final LocationList masterList = new LocationList();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// set up
		DBAdapter database = DBAdapter.getInstance();
		Location startLocation = database.queryLocation("Location_ID", "3").get(0);
		Animal startAnimal = database.queryAnimal("Location_ID", "3").get(0);
		masterList.addLocation( startLocation);
		startLocation.addAnimal(startAnimal);

		//display initial state
		System.out.println("APPLICATION START");
		System.out.println("Location in master List: " + masterList.getLocation(3));
		System.out.printf("Animal in %s: %s", startLocation.getName(), startAnimal.toString());

		/*-------------------- TEST SOMEWHERE AFTER THIS --------------------*/

	}
}

