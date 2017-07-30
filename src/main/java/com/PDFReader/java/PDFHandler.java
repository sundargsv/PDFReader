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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.IOException;

public class PDFHandler {

	private static final Logger LOGGER = Logger.getLogger(PDFHandler.class.getName());
	private static PdfReader pdfReader;
	
	public PDFHandler() {
		super();
		Logger.getLogger(PDFHandler.class.getName());
		
	}

	/*
	 * @param : Directory path
	 * @Resolved : Response
	 * */
	@SuppressWarnings("unchecked")
	public String contentExtractor(String dirPath){
		
		String pageContent;
		File dir = new File(dirPath);
		String[] files = dir.list();
		 Rectangle rect = new Rectangle(36, 36, 300, 500);
	        RenderFilter regionFilter = new RegionTextRenderFilter(rect);
	        /*FontRenderFilter fontFilter = new FontRenderFilter();*/
	        TextExtractionStrategy strategy = new FilteredTextRenderListener(
	                new LocationTextExtractionStrategy(), regionFilter);
		LOGGER.info("No of files :" + files.length);
		if(files.length != 0){
			
			 for (String file : files) {
				 
				 try {
					 	/*
					 	 *Create PdfReader instance.
					 	 * Get the number of pages in pdf.
					 	 * Iterate the pdf through pages.
					 	 * Extract the page content using PdfTextExtractor
					 	 * Print the page content on console.
					 	 * */
						
						pdfReader = new PdfReader(dirPath+"/"+file);	
						
						long pages = pdfReader.getNumberOfPages();
						
						for(int i = 1; i <= pages; i++) { 
						  pageContent = 
						  	PdfTextExtractor.getTextFromPage(pdfReader, i, strategy);
						  //System.out.println("Content on Page "+ i + ": " + pageContent);
						  
						  /*
						   * Send Json
						   * BAN : "123456789"
						   * billAmount : "100.50$"
						   * query : "why my bill amount is 5.50 $ extra ?"
						   * obj.toJSONString()
						   *  */
						  
						  String arr[] = pageContent.split(" : ", 10);
						  JSONObject obj = new JSONObject();
						  obj.put("billingAccountNumber", arr[1]);
						  obj.put("billAmount", "100.50");
						  obj.put("query", "why my bill amount is 5.50 $ extra");
						  
					
						  @SuppressWarnings("unused")
						String res = PDFHandler.requestHandler(obj);
						  
					      }
					
				
				   pdfReader.close();
				 } catch (IOException e) {
					 LOGGER.warning("IO 	EXCEPTION : "+e.getMessage());
					 e.printStackTrace();
				 }catch (Exception e) {
					 LOGGER.severe("EXCEPTION : "+e.getMessage());
					 e.printStackTrace();
				 }
			 }
		
		}
		
		return "Query Responded !!!";
			
	}
	
	/*
	 * @param : JSONObject (BAN, Bill Amount, Query)
	 * @Resolved : Response
	 * */
	
	public static String requestHandler(JSONObject jsonObject) {
		String output = "";
		 try {

				URL url = new URL("http://localhost:8080/respondQuery");
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
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
				conn.disconnect();

			  } catch (MalformedURLException e) {
				 LOGGER.warning("MAL FORMED URL EXCEPTION : "+e.getMessage()); 
				 e.printStackTrace();

			  } catch (IOException e) {
				LOGGER.warning("IO EXCEPTION : "+e.getMessage());
				e.printStackTrace();

			 }
		 
		 	return output;
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
