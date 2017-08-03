package com.PDFReader.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.PDFReader.java.PDFHandler;
import com.PDFReader.models.MessageTemplate;

@RestController
public class AppController {

	private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());

	//Auto wiring
//	@SuppressWarnings("unused")
//	@Autowired
//	private MessageTemplate responseTemplate;
	
	private Properties PROPERTY;
	private InputStream INPUT_STREAM;
	private PDFHandler PDF_HANDLER;
	private String DIRECTORY_PATH;
	
	
	public AppController() {
		super();
		Logger.getLogger(AppController.class.getName());
		PROPERTY = new Properties();
		INPUT_STREAM = null;
		PDF_HANDLER = new PDFHandler();
		DIRECTORY_PATH = null;
		
//		try {
//
//			INPUT_STREAM = new FileInputStream("config.properties");
//			PROPERTY.load(INPUT_STREAM);
//			DIRECTORY_PATH = PROPERTY.getProperty("com.PDFReader.directory.src");
//			
//			LOGGER.info(DIRECTORY_PATH);
//		} catch (IOException ex) {
//			LOGGER.severe("IO EXCEPTION :" + ex);
//			ex.printStackTrace();
//		} finally {
//			if (INPUT_STREAM != null) {
//				try {
//					INPUT_STREAM.close();
//				} catch (IOException e) {
//					LOGGER.severe("IO EXCEPTION :" + e);
//					e.printStackTrace();
//				}
//			}
//		}
		
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "PDFHandler Works !!!";
	}
	
	@RequestMapping(value = "/triggerPDFReader", method = RequestMethod.GET, headers = "Accept=application/json")
	public String triggerPDFHandler() {
		String response = PDF_HANDLER.contentExtractor("/Users/mopus/Documents/pdfs");
		return response;
		
	} 
}

