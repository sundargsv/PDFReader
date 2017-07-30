package com.PDFReader.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.PDFReader.models.RequestTemplate;
import com.PDFReader.models.ResponseTemplate;

@RestController
public class SampleLiveBotIO {

	private static final String BILLING_ACCOUNT_NUMBER = "9688265787";
	private static final double DEFAULT_BILLING_AMOUNT = 0;
	private ResponseTemplate responseTemplate = new ResponseTemplate();
	
		/* 
		 * @Response : Test API
		 * @method   : GET
		 * **/
		@RequestMapping("/APICheker")
		public String welcome() {
			return "Bingo, Initiate the call now !!";
		}
	
		/* 
		 * @param    : Request a query with the PDF
		 * @Response : Responding by maintaining the context
		 * @method   : POST
		 * **/
		@RequestMapping(value = "/respondQuery", method = RequestMethod.POST, headers = "Accept=application/json")
		public ResponseTemplate respondeToQuery(@RequestBody RequestTemplate requestTemplate) {
			
			if(requestTemplate != null) {
				responseTemplate.setContextTag(1);
				
				if(requestTemplate.getBillingAccountNumber().equals(BILLING_ACCOUNT_NUMBER)) {
					if(requestTemplate.getBillAmount() != DEFAULT_BILLING_AMOUNT) {
						if(requestTemplate.getQuery() != null) {
							System.out.println("Please wait while we find the best possible answer/ response");
							responseTemplate.setRespone("That is your Late Fee/ Penalty charge from your previous bill.");
						}else{
							responseTemplate.setRespone("What is your Query ?");
						}
					}
				}else {
					responseTemplate.setRespone("Give me your BAN no ?");
				}
			}
			
			return responseTemplate;
		}
}
