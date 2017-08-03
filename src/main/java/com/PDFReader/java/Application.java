package com.PDFReader.java;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.PDFReader.api")
public class Application extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		/*
		 * Application : PDFReader
		 * PDFHandler  : Gets the uploaded files, parse it
		 * contentExtractor : Extracts the specific info and sends back to Request Handler
		 * Request Handler :" This guy will Hit the API to get the response and makes this response visible to the customer
		 * **/
		
		SpringApplication.run(Application.class, args);
		System.out.println(" Application started to read PDF ");

		

	}

}