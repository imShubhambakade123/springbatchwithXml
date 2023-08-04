package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.CBLO;

public class CBLOProcessor implements ItemProcessor<CBLO, CBLO> {

	@Override
	public CBLO process(CBLO cblo) throws Exception {
      String emailid = cblo.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				cblo.setUSERNAME(emailid.substring(0,atIndex));
				cblo.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(cblo);
		return cblo;
	}

}
