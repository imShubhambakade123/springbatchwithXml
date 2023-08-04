package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.ForexTr;

public class ForexTrProcessor implements ItemProcessor<ForexTr, ForexTr>{

	@Override
	public ForexTr process(ForexTr forexTr) throws Exception {
        String emailid = forexTr.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				forexTr.setUSERNAME(emailid.substring(0,atIndex));
				forexTr.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(forexTr);
		return forexTr;
	}

}
