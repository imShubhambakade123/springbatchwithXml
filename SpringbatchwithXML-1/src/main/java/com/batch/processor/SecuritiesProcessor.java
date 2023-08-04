package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Securities;

public class SecuritiesProcessor implements ItemProcessor<Securities, Securities> {

	@Override
	public Securities process(Securities securities) throws Exception {
		
      String emailid = securities.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				securities.setUSERNAME(emailid.substring(0,atIndex));
				securities.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(securities);
		return securities;
	}

}
