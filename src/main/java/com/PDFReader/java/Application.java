package com.PDFReader.java;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

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
	
		PDFHandler pdfReader = new PDFHandler();
		/*String result = pdfReader.contentExtractor("/Users/mopus/Documents/pdfs");*/
		String result = pdfReader.contentExtractor("/home/sundar/My-Projects/pdfs");
//		System.out.println(pdfReader.pdfWriter());

	}

}