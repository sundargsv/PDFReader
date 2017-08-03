package com.PDFReader.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private String BASE_PATH;
	private Properties PROPERTY;
	private InputStream INPUT_STREAM;
	private PDFHandler PDF_HANDLER;
	private String DIRECTORY_PATH;
	private String API_URL_PATH;
	
	
	public AppController() {
		super();
		Logger.getLogger(AppController.class.getName());
		BASE_PATH = null;
		PROPERTY = new Properties();
		INPUT_STREAM = null;
		PDF_HANDLER = new PDFHandler();
		DIRECTORY_PATH = null;
		API_URL_PATH = null;
		
		try {
			
			BASE_PATH = System.getProperty("catalina.base") ;
			LOGGER.info("*****************************************************************************");
			LOGGER.info("BASE_PATH :" + BASE_PATH);
			INPUT_STREAM = new FileInputStream(BASE_PATH+File.separator+"config.properties");
			PROPERTY.load(INPUT_STREAM);
			DIRECTORY_PATH = PROPERTY.getProperty("com.PDFReader.directory.src");
			API_URL_PATH = PROPERTY.getProperty("com.PDFReader.LiveBot.URL");
			LOGGER.info("DIRECTORY_PATH :" + DIRECTORY_PATH);
			LOGGER.info("API_URL_PATH :" + API_URL_PATH);
			LOGGER.info("*****************************************************************************");
			
		} catch (IOException ex) {
			LOGGER.severe("IO EXCEPTION :" + ex);
			ex.printStackTrace();
		} finally {
			if (INPUT_STREAM != null) {
				try {
					INPUT_STREAM.close();
				} catch (IOException e) {
					LOGGER.severe("IO EXCEPTION :" + e);
					e.printStackTrace();
				}
			}
		}
		
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "PDFHandler Works !!!";
	}
	
	@RequestMapping(value = "/triggerPDFReader/{fileName}", method = RequestMethod.GET, headers = "Accept=application/json")
	public MessageTemplate triggerPDFHandler(@PathVariable String fileName) {
		return PDF_HANDLER.contentExtractor(DIRECTORY_PATH, API_URL_PATH, fileName);
	} 
}

