package com.sphericalelephant.zeitgeistng.service.processor;


import com.sphericalelephant.zeitgeistng.data.Tag;

import at.diamonddogs.service.processor.GsonProcessor;

public class TagsProcessor extends AbstractProcessor<Tag[]> {
	public static final int ID = 1000000002;

	public TagsProcessor() {
		super(Tag[].class, ID);
	}
}
