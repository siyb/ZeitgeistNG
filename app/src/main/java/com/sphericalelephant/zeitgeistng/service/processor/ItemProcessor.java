package com.sphericalelephant.zeitgeistng.service.processor;

import com.sphericalelephant.zeitgeistng.data.Item;

import at.diamonddogs.service.processor.GsonProcessor;


public class ItemProcessor extends AbstractProcessor<Item> {
	public static final int ID = 1000000000;

	public ItemProcessor() {
		super(Item.class, ID);
	}
}
