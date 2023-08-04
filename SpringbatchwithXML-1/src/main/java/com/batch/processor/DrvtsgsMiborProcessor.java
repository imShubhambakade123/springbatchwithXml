package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.DrvtsgsMibor;

public class DrvtsgsMiborProcessor implements ItemProcessor<DrvtsgsMibor, DrvtsgsMibor> {

	@Override
	public DrvtsgsMibor process(DrvtsgsMibor drvtsgsMibor) throws Exception {
		String emailid = drvtsgsMibor.getEMAILID();
		
		if (emailid != null) {
			
			int atIndex = emailid.indexOf('@');
			if (atIndex >= 0) {
				drvtsgsMibor.setUSERNAME(emailid.substring(0,atIndex));
				drvtsgsMibor.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		return drvtsgsMibor;
	}
}	
	


//	@Override
//	public DrvtsgsMibor process(DrvtsgsMibor drvtsgsMibor) throws Exception {
//		String emailid = drvtsgsMibor.getEMAILID();
//		
//		if (emailid != null) {
//	        String[] emailids = emailid.split(";"); // Assuming email addresses are separated by semicolon
//	        for (String emailId : emailids) {
//	            int atIndex = emailId.indexOf('@');
//	            if (atIndex >= 0) {
//	                drvtsgsMibor.setUSERNAME(emailId.substring(0, atIndex));
//	                drvtsgsMibor.setDOMAIN(emailId.substring(atIndex + 1));
//
//	                
//	                System.out.println(drvtsgsMibor);
//	            }
//	        }
//	    }
//		return drvtsgsMibor;
//	}	
	            

	
	


