package com.sphericalelephant.zeitgeistng.service.processor;

import com.sphericalelephant.zeitgeistng.data.Items;

import at.diamonddogs.service.processor.GsonProcessor;

public class ItemsProcessor extends AbstractProcessor<Items> {
	public static final int ID = 1000000001;

	public ItemsProcessor() {
		super(Items.class, ID);
	}
}
