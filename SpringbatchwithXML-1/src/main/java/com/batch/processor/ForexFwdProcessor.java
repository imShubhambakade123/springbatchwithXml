package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.ForexFwd;

public class ForexFwdProcessor implements ItemProcessor<ForexFwd, ForexFwd> {

	@Override
	public ForexFwd process(ForexFwd forexFwd) throws Exception {
       String emailid = forexFwd.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				forexFwd.setUSERNAME(emailid.substring(0,atIndex));
				forexFwd.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(forexFwd);
		return forexFwd;
	}

}
