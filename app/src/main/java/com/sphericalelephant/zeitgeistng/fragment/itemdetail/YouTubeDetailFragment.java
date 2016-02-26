package com.sphericalelephant.zeitgeistng.fragment.itemdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.util.YouTubeUrlParser;

public class YouTubeDetailFragment extends AbstractItemDetailFragment {
	private YouTubePlayerSupportFragment youtubePlayer;
	private YouTubePlayer player;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		youtubePlayer = YouTubePlayerSupportFragment.newInstance();
		// TODO: make api key configurable
		youtubePlayer.initialize("AIzaSyDowzbO_pHNexJPFMTX60oO-sazwSwDaZo", new YouTubePlayer.OnInitializedListener() {
			@Override
			public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
				player = youTubePlayer;
				showItem();
			}

			@Override
			public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
				// TODO: implement user error handling here later, atm this whole POS seems broken, we'll therefore ignore the error
				//	if (youTubeInitializationResult.isUserRecoverableError()) {
				//			youTubeInitializationResult.getErrorDialog()
				//			youTubeInitializationResult.getErrorDialog(getActivity(), 1).show();
				//		} else {
				player = null;
				showItem();
				//		}
			}
		});

		View v = inflater.inflate(R.layout.fragment_youtubevideofragment, container, false);
		v.setOnTouchListener(onTouchListener);
		return v;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getChildFragmentManager()
				.beginTransaction()
				.add(R.id.fragment_youtubevideofragment_fl_container, youtubePlayer)
				.commit();
	}

	@Override
	protected boolean shouldShowItemAfterViewCreation() {
		return false;
	}

	@Override
	protected void showItem() {
		String sourceUrl = currentlyDisplayedItem.getSource();
		String youtubeVideoId = YouTubeUrlParser.getInstance().getVideoId(sourceUrl);
		if (youtubeVideoId == null) {
			handleUnknowFormat(sourceUrl, R.string.fragment_itemdetailfragment_error_unknownhoster);
			return;
		}
		if (player == null) { // if player could not be initialized
			handleUnknowFormat(sourceUrl, R.string.fragment_itemdetailfragment_error_youtubeplayerinit);
			return;
		}
		player.cueVideo(youtubeVideoId);
	}
}
