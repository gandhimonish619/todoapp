package com.husqvarna.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starter class for the spring application.
 */
@SpringBootApplication
public class TodoappApplication {
	/**
	 * This method is the entry point for the Spring Boot Application
	 *
	 * @param args argument to the main method
	 */
	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

}
