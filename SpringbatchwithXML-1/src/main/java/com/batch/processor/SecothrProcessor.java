package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Secothr;

public class SecothrProcessor implements ItemProcessor<Secothr, Secothr> {

	@Override
	public Secothr process(Secothr secothr) throws Exception {
      String emailid = secothr.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				secothr.setUSERNAME(emailid.substring(0,atIndex));
				secothr.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(secothr);
		return secothr;
	}

}
