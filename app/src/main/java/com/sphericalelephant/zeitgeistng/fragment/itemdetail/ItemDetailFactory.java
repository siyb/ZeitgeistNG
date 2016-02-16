package com.sphericalelephant.zeitgeistng.fragment.itemdetail;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sphericalelephant.zeitgeistng.data.Item;

public class ItemDetailFactory {
	private static ItemDetailFactory INSTANCE;

	private ItemDetailFactory() {

	}

	@NonNull
	public synchronized static ItemDetailFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ItemDetailFactory();
		}
		return INSTANCE;
	}

	@Nullable
	public AbstractItemDetailFragment newInstance(@NonNull Item item) {
		String mimeType = item.getMimetype();
		if (mimeType.matches("video/.*")) { // video
			return new VideoDetailFragment();
		} else if (mimeType.matches("image/.*")) {
			if (item.getType() == Item.ItemType.VIDEO) { // most likely a video hoster
				return new YouTubeDetailFragment();
			}
			return new ImageDetailFragment();
		}
		return null;
	}
}
