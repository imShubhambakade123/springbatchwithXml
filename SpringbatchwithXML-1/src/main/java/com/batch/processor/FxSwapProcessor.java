package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.FxSwap;

public class FxSwapProcessor implements ItemProcessor<FxSwap, FxSwap> {

	@Override
	public FxSwap process(FxSwap fxSwap) throws Exception {
       String emailid = fxSwap.getEMAILID();
		
		if (emailid != null) {
			int atIndex = emailid.indexOf('@');
			if(atIndex >= 0) {
				fxSwap.setUSERNAME(emailid.substring(0,atIndex));
				fxSwap.setDOMAIN(emailid.substring(atIndex + 1));
			}
		}
		System.out.println(fxSwap);
		return fxSwap;
	}

}
