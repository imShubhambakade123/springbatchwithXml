package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.DrvtsgsMifor;

public class DrvtsgsMiforProcessor implements ItemProcessor<DrvtsgsMifor, DrvtsgsMifor>{

	@Override
	public DrvtsgsMifor process(DrvtsgsMifor drvtsgsMifor) throws Exception {
       String emailid = drvtsgsMifor.getEMAILID();
		
		if (emailid != null) {
			
			int atIndex = emailid.indexOf('@');
			if (atIndex >= 0) {
				drvtsgsMifor.setUSERNAME(emailid.substring(0,atIndex));
				drvtsgsMifor.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		return drvtsgsMifor;
	}
}
