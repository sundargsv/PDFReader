package com.PDFReader.java;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@ComponentScan("com.PDFReader.api")
public class Application {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		/*
		 * Application : PDFReader
		 * PDFHandler  : Gets the uploaded files, parse it
		 * contentExtractor : Extracts the specific info and sends back to Request Handler
		 * Request Handler :" This guy will Hit the API to get the response and makes this response visible to the customer
		 * **/
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		System.out.println(" Application started to read PDF ");
	
		
		/*
		 * Load the property file
		 * Get the Property value of 
		 * 
		 * **/
		
		Properties prop = new Properties();
		InputStream input = null;
		PDFHandler pdfReader = new PDFHandler();
		try {

			input = new FileInputStream("config.properties");
			prop.load(input);
			String result = pdfReader.contentExtractor(prop.getProperty("com.PDFReader.directory.src"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

	}

}