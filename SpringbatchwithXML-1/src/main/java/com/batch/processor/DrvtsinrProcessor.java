package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Drvtsinr;

public class DrvtsinrProcessor implements ItemProcessor<Drvtsinr, Drvtsinr> {

	@Override
	public Drvtsinr process(Drvtsinr drvtsinr) throws Exception {
     String emailid = drvtsinr.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				drvtsinr.setUSERNAME(emailid.substring(0,atIndex));
				drvtsinr.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(drvtsinr);
		return drvtsinr;
	}

}
