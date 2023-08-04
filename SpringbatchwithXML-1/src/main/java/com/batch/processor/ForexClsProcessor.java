package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.ForexCls;

public class ForexClsProcessor implements ItemProcessor<ForexCls, ForexCls> {

	@Override
	public ForexCls process(ForexCls forexClx) throws Exception {
       String emailid = forexClx.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				forexClx.setUSERNAME(emailid.substring(0,atIndex));
				forexClx.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(forexClx);
		return forexClx;
	}

}
