package com.sphericalelephant.zeitgeistng.fragment.itemdetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sphericalelephant.zeitgeistng.R;
import com.squareup.picasso.Picasso;

public class ImageDetailFragment extends AbstractItemDetailFragment {
	private ImageView imageView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		imageView = (ImageView) inflater.inflate(R.layout.fragment_imagedetailfragment, container, false);
		return imageView;
	}

	@Override
	protected void showItem() {
		if (imageView == null || currentlyDisplayedItem == null) {
			return;
		}
		Picasso
				.with(getContext())
				.load(getUrlFromCurrentItem())
				.into(imageView);
	}

}
