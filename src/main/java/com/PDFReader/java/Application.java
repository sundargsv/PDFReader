package com.PDFReader.java;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		System.out.println(" Application started to read PDF ");
		
		PDFHandler pdfReader = new PDFHandler();
		String result = pdfReader.getContent("/home/sunder/pdfs");
		System.out.println(result);

	}

}