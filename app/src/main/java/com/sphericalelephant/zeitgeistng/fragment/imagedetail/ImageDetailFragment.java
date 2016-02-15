package com.sphericalelephant.zeitgeistng.fragment.imagedetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.data.Item;
import com.sphericalelephant.zeitgeistng.service.buider.WebRequestBuilder;
import com.squareup.picasso.Picasso;

public class ImageDetailFragment extends DialogFragment {
    private ImageView imageView;

    private Item currentlyDisplayedItem;

    public static ImageDetailFragment newInstance() {
        return new ImageDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = (ImageView) inflater.inflate(R.layout.fragment_imagedetailfragment, container, false);
        return imageView;
    }

    @Override
    public void onResume() {
        super.onResume();
        showItemImage();
    }

    public void showWithImage(@NonNull FragmentManager fm, String tag, @NonNull Item item) {
        if (getDialog() == null || !getDialog().isShowing()) {
            show(fm, tag);
        }
        currentlyDisplayedItem = item;
        showItemImage();
    }

    private void showItemImage() {
        if (imageView == null || currentlyDisplayedItem == null) {
            return;
        }
        Uri url = Uri
                .parse(WebRequestBuilder.URL)
                .buildUpon()
                .appendPath(currentlyDisplayedItem.getImage().getImageUrl())
                .build();
        Picasso
                .with(getContext())
                .load(url)
                .into(imageView);
    }
}
