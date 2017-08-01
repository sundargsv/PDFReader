package com.PDFReader.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.PDFReader.models.MessageTemplate;

@RestController
public class SampleLiveBotIO {

	private static final String BILLING_ACCOUNT_NUMBER = "458281644";
	private static final double DEFAULT_BILLING_AMOUNT = 109.83;
	private MessageTemplate responseTemplate = new MessageTemplate();
	
		/* 
		 * @Response : Test API
		 * @method   : GET
		 * **/
		@RequestMapping("/APICheker")
		public String welcome() {
			return "Bingo, Initiate the API call now !!";
		}
	
		/* 
		 * @param    : Request a query with the PDF
		 * @Response : Responding by maintaining the context
		 * @HttpMethod   : POST
		 * **/
		@RequestMapping(value = "/respondQuery", method = RequestMethod.POST, headers = "Accept=application/json")
		public MessageTemplate respondeToQuery(@RequestBody MessageTemplate requestTemplate) {
			
			if(requestTemplate != null) {
				responseTemplate.setMessage("That is your late payment fee from your previous bill !!!");
				
			}else{
				responseTemplate.setContextTag("");
				responseTemplate.setMessage("Sorry, I din't understand what you mean");
			}
			
			return responseTemplate;
		}
}
		
		
