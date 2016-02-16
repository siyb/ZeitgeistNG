package com.sphericalelephant.zeitgeistng.fragment.itemdetail;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFacade;
import com.sphericalelephant.zeitgeistng.service.buider.WebRequestBuilder;

public class VideoDetailFragment extends AbstractItemDetailFragment {
	private VideoView videoView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		videoView = (VideoView) inflater.inflate(R.layout.fragment_videodetailfragment, container, false);
		return videoView;
	}

	@Override
	protected void showItem() {
		final Uri videoUrl = PreferenceFacade.getInstance().getHostAddress(getContext()).buildUpon().appendPath(currentlyDisplayedItem.getImage().getImageUrl()).build();

		videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				handleUnknowFormat(videoUrl, R.string.fragment_itemdetailfragment_error_normalvideo);
				return true;
			}
		});
		videoView.setVideoURI(videoUrl);
		videoView.setMediaController(new MediaController(getContext()));
		videoView.start();
	}
}
