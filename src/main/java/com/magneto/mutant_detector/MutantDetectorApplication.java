package com.magneto.mutant_detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantDetectorApplication {

	public static void main(String[] args) {
		System.out.println("API de deteccion de mutantes iniciada");
		SpringApplication.run(MutantDetectorApplication.class, args);
	}

}
