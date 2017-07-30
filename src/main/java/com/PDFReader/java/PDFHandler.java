package com.PDFReader.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Logger;

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
	 * @param : Content
	 * */
	public String getContent(String dirPath){
		
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
						  System.out.println("Content on Page "
						  		              + i + ": " + pageContent);
					      }
					 
					      //Close the PdfReader.
					      pdfReader.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }catch (Exception e) {
					 e.printStackTrace();
				 }
			 }
		
		}
		
		return "No of files :";
			
	}
	
	
	public boolean pdfWriter(){
		Document document = new Document();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("/home/sunder/pdfs/itext.pdf")));

            //open
            document.open();

            Paragraph p = new Paragraph();
            p.add("Account No : 9688265787");
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
           document.add(new Paragraph("hi"));
            
            document.close();

            System.out.println("Done");
            return true;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
		
		
	}

}
