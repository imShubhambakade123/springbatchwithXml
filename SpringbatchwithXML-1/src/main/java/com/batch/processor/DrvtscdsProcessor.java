package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Drvtscds;

public class DrvtscdsProcessor implements ItemProcessor<Drvtscds, Drvtscds> {

	@Override
	public Drvtscds process(Drvtscds drvtscds) throws Exception {
     String emailid = drvtscds.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				drvtscds.setUSERNAME(emailid.substring(0,atIndex));
				drvtscds.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(drvtscds);
		return drvtscds;
	}

}
