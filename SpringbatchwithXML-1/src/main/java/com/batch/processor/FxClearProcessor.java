package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.FxClear;

public class FxClearProcessor implements ItemProcessor<FxClear, FxClear> {

	@Override
	public FxClear process(FxClear fxClear) throws Exception {
		// TODO Auto-generated method stub
		return fxClear;
	}

	

}
