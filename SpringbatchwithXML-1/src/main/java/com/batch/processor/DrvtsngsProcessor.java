package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Drvtsngs;

public class DrvtsngsProcessor implements ItemProcessor<Drvtsngs, Drvtsngs> {

	@Override
	public Drvtsngs process(Drvtsngs drvtsngs) throws Exception {
		
       String emailid = drvtsngs.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				drvtsngs.setUSERNAME(emailid.substring(0,atIndex));
				drvtsngs.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(drvtsngs);
		return drvtsngs;
	}

}
