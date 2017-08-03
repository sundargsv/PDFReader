package com.PDFReader.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.PDFReader.models.MessageTemplate;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.IOException;

public class PDFHandler {

	private static final Logger LOGGER = Logger.getLogger(PDFHandler.class.getName());
	private static PdfReader PDFREADER;
	private static Gson GSON;
	private MessageTemplate MESSAGETEMPLATE;

	
	public PDFHandler() {
		super();
		Logger.getLogger(PDFHandler.class.getName());
		GSON = new Gson();
		MESSAGETEMPLATE = null;
	}

	/*
	 * @param : Directory path
	 * @Resolved : Response
	 * */
	@SuppressWarnings("unchecked")
	public MessageTemplate contentExtractor(String dirPath, String API_URL_PATH, String fileName){
		
		String pageContent = null;
		
		if(fileName != null){
				 
				 try {
					 	/*
					 	 *Create PdfReader instance.
					 	 * Get the number of pages in PDF.
					 	 * Iterate the PDF through pages.
					 	 * Extract the page content using PdfTextExtractor
					 	 * Print the page content on console.
					 	 * */
						
						PDFREADER = new PdfReader(dirPath+"/"+fileName+".pdf");	
						pageContent = 
							  	PdfTextExtractor.getTextFromPage(PDFREADER, 1);
						String[] lines =  pageContent.split("\n");
						StringBuilder message = new StringBuilder();
						message.append("Hi my billing account number is ");
						
						for (int i = 0; i < lines.length; i++) {
							/*
							   * Send JSON
							   * { contextTag : ""
							   * query : "Hi my billing account number is 458281644 and can you tell me why my bill amount has increased ?"
							   * }
							   * obj.toJSONString() 
							   * */
							if(lines[i].contains("Account Number")){
								String[] tokenize = lines[i].split(" ");
								message.append(tokenize[2]);
								break;
								
							}
							/*else if(lines[i].contains("Bill Date")){
								String[] tokenize = lines[i].split(": ");

							}
							else if(lines[i].contains("Hello,")){
								String[] tokenize = lines[i].split(", ", 2);
								System.out.println(tokenize[1]);
							}else if(lines[i].contains("Total Amount Due")){
								String[] tokenize = lines[i].split(" ");
								request.put("billAmount", tokenize[3].substring(1));
							}else{
								continue;
							}*/
		
						}
						message.append(" and can you tell me why my bill amount has increased ?");
						JSONObject request = new JSONObject();
						request.put("contextTag", "no");
						request.put("message", message.toString());
						System.out.println(request.toJSONString());
						
						String result  = PDFHandler.requestHandler(request, API_URL_PATH);
						/*LOGGER.info("My JSONSTRING :" +result);*/
						MESSAGETEMPLATE = new MessageTemplate();
						MESSAGETEMPLATE = GSON.fromJson(result, MessageTemplate.class);
						LOGGER.info(MESSAGETEMPLATE.toString());
						
				   PDFREADER.close();
				   
				 } catch (IOException e) {
					 LOGGER.warning("IO EXCEPTION : "+e.getMessage());
					 e.printStackTrace();
					 MESSAGETEMPLATE.setContextTag("");
					 MESSAGETEMPLATE.setMessage("Sorry, There was an internal server error. Please try again later or contact your admin");
				 }catch (Exception e) {
					 LOGGER.severe("EXCEPTION : "+e.getMessage());
					 e.printStackTrace();
					 MESSAGETEMPLATE.setContextTag("");
					 MESSAGETEMPLATE.setMessage("Sorry, There was an internal server error. Please try again later or contact your admin");
				 }
			 
		
		}
		
		return MESSAGETEMPLATE;
			
	}
	
	/*
	 * @param : JSONObject (BAN, Bill Amount, Query)
	 * @Resolved : Response
	 * */
	
	public static String requestHandler(JSONObject jsonObject, String API_URL_PATH) {
		String output;
		
		 try {

				URL url = new URL(API_URL_PATH);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				OutputStream os = conn.getOutputStream();
				os.write(jsonObject.toString().getBytes());
				os.flush();

				if (conn.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				
				System.out.println("Please wait for a while, we are trying to respond you soon .... \n");
				output = br.readLine();
				
				conn.disconnect();
				
				return output;
			  } catch (MalformedURLException e) {
				 LOGGER.warning("MAL FORMED URL EXCEPTION : "+e.getMessage()); 
				 e.printStackTrace();

			  } catch (IOException e) {
				LOGGER.warning("IO EXCEPTION : "+e.getMessage());
				e.printStackTrace();

			 }
		 
		 	return null;
		 	
	}
	
	/*
	 * @param : N/ A
	 * @Resolved : Written Successfully (True/ False)
	 * */
	
	public boolean pdfWriter(){
		Document document = new Document();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("/Users/mopus/Documents/pdfs/itext.pdf")));
            document.open();

            Paragraph p = new Paragraph();
            p.add("Account_Number : 9688265787");
            p.setAlignment(Element.ALIGN_CENTER);

            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(36, 36, 300, 500);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(2);
            
            canvas.rectangle(rect);
            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(rect);
            ct.addElement(new Paragraph(p));
            ct.go();
           document.add(new Paragraph("THIS IS YOUR CURRENT BILL STATEMENT WITH DUE DATE ON AUG 20, 2017"));
            
            document.close();
            LOGGER.info("It's Done PDF Bill Generated !!!");
            return true;
        } catch (DocumentException e) {
        	   	LOGGER.warning("DOCUMENT EXCEPTION : "+e.getMessage());
        	   	e.printStackTrace();
        } catch (IOException e) {
        		LOGGER.warning("IO EXCEPTION : "+e.getMessage());
            e.printStackTrace();
        }
		return false;
		
		
	}

}
