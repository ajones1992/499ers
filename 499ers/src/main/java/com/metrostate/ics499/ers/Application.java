package com.metrostate.ics499.ers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;


@Order(2)
@SpringBootApplication
public class Application implements CommandLineRunner {
	private static LocationList masterList = new LocationList();

	public static LocationList getMasterList(){
		return masterList;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// set up
		masterList.loadDatabaseIntoMap();

		//display initial state
		System.out.println("APPLICATION START");
		System.out.println("Everything in master List: ");
		System.out.println(masterList);

		/*-------------------- TEST SOMEWHERE AFTER THIS --------------------*/

	}
}

