package com.sphericalelephant.zeitgeistng.service.processor;

import at.diamonddogs.service.processor.GsonProcessor;

public abstract class AbstractProcessor<OUTPUT> extends GsonProcessor<OUTPUT> {

	public AbstractProcessor(Class<OUTPUT> clazz, int processorId) {
		super(clazz, processorId);
	}
/*
	@Override
	protected Gson buildGson() {
		return new GsonBuilder().registerTypeAdapter(Date.class, new GsonIso8601Datelizer()).create();
	}
	*/
}
