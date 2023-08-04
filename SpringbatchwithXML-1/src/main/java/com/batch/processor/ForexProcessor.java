package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Forex;

public class ForexProcessor implements ItemProcessor<Forex, Forex> {

	@Override
	public Forex process(Forex forex) throws Exception {
       String emailid = forex.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				forex.setUSERNAME(emailid.substring(0,atIndex));
				forex.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(forex);
		return forex;
	}

}
