package com.metrostate.ics499.ers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;


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
		System.out.println("APPLICATION START");
		/*-------------------- TEST SOMEWHERE AFTER THIS --------------------*/

	}
}

