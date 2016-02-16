package com.sphericalelephant.zeitgeistng.fragment.imagedetail;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.data.Item;
import com.sphericalelephant.zeitgeistng.service.buider.WebRequestBuilder;
import com.sphericalelephant.zeitgeistng.util.YouTubeUrlParser;
import com.squareup.picasso.Picasso;

public class ItemDetailFragment extends DialogFragment {
	private ImageView imageView;
	private VideoView videoView;
	private FrameLayout youtubePlayerContainer;
	private YouTubePlayerSupportFragment youtubePlayer;
	private YouTubePlayer player;

    private Item currentlyDisplayedItem;

	public enum MediaType {
		VIDEO,
		VIDEO_YOUTUBE,
		IMAGE
	}

	public static ItemDetailFragment newInstance() {
		return new ItemDetailFragment();
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
		View v = inflater.inflate(R.layout.fragment_imagedetailfragment, container, false);
		imageView = (ImageView) v.findViewById(R.id.fragment_imagedetailfragment_iv_imageview);
		videoView = (VideoView) v.findViewById(R.id.fragment_imagedetailfragment_vv_videoview);
		youtubePlayerContainer = (FrameLayout) v.findViewById(R.id.fragment_imagedetailfragment_fl_ytcontainer);
		youtubePlayer = YouTubePlayerSupportFragment.newInstance();
		getChildFragmentManager()
				.beginTransaction()
				.add(R.id.fragment_imagedetailfragment_fl_ytcontainer, youtubePlayer)
				.commit();
		// TODO: make api key configurable
		youtubePlayer.initialize("", new YouTubePlayer.OnInitializedListener() {
			@Override
			public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
				player = youTubePlayer;
			}

			@Override
			public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
				// TODO: implement user error handling here later, atm this whole POS seems broken, we'll therefore ignore the error
				//	if (youTubeInitializationResult.isUserRecoverableError()) {
				//			youTubeInitializationResult.getErrorDialog()
				//			youTubeInitializationResult.getErrorDialog(getActivity(), 1).show();
				//		} else {
				player = null;
				//		}
			}
		});
		return v;
	}

    @Override
    public void onResume() {
        super.onResume();
		showItem();
	}

	private void showItem() {
		if (currentlyDisplayedItem == null) {
			return;
		}
		switch (currentlyDisplayedItem.getType()) {
			case IMAGE:
				showItemImage();
				break;
			case VIDEO:
				showVideo();
				break;
		}
	}

	public void showWithImage(@NonNull FragmentManager fm, String tag, @NonNull Item item) {
		if (getDialog() == null || !getDialog().isShowing()) {
			show(fm, tag);
		}
		hideViewsAccoringToType(MediaType.IMAGE);
		currentlyDisplayedItem = item;
		showItem();
	}

	private void showVideo() {
		if (imageView == null || currentlyDisplayedItem == null) {
			return;
		}

		if (currentlyDisplayedItem.getMimetype().matches("video/.*")) { // regular video
			hideViewsAccoringToType(MediaType.VIDEO);
			imageView.setVisibility(View.INVISIBLE);
			videoView.setVisibility(View.VISIBLE);
			videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					handleUnknowFormat(R.string.fragment_itemdetailfragment_error_normalvideo);
					return true;
				}
			});
			videoView.setVideoURI(Uri.parse(currentlyDisplayedItem.getSource()));


		} else { // check for yt video
			String youtubeVideoId = YouTubeUrlParser.getInstance().getVideoId(currentlyDisplayedItem.getSource());
			if (youtubeVideoId == null) {
				handleUnknowFormat(R.string.fragment_itemdetailfragment_error_unknownhoster);
				return;
			}

			hideViewsAccoringToType(MediaType.VIDEO_YOUTUBE);
			if (player == null) { // if player could not be initialized
				handleUnknowFormat(R.string.fragment_itemdetailfragment_error_youtubeplayerinit);
				return;
			}

			player.cueVideo(youtubeVideoId);
		}
	}

	private void handleUnknowFormat(@StringRes int message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
		dismiss();
	}

    private void showItemImage() {
        if (imageView == null || currentlyDisplayedItem == null) {
            return;
        }
		imageView.setVisibility(View.VISIBLE);
		videoView.setVisibility(View.INVISIBLE);
		Picasso
				.with(getContext())
				.load(getUrlFromCurrentItem())
				.into(imageView);
	}

	private void hideViewsAccoringToType(MediaType mt) {
		if (youtubePlayer == null || videoView == null || imageView == null) return;
		youtubePlayerContainer.setVisibility(mt == MediaType.VIDEO_YOUTUBE ? View.VISIBLE : View.INVISIBLE);
		videoView.setVisibility(mt == MediaType.VIDEO ? View.VISIBLE : View.INVISIBLE);
		imageView.setVisibility(mt == MediaType.IMAGE ? View.VISIBLE : View.INVISIBLE);
	}

	private Uri getUrlFromCurrentItem() {
		return Uri
				.parse(WebRequestBuilder.URL)
				.buildUpon()
				.appendPath(currentlyDisplayedItem.getImage().getImageUrl())
				.build();
	}
}
