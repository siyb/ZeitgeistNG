package com.sphericalelephant.zeitgeistng.fragment.itemdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.data.Item;
import com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFacade;

public abstract class AbstractItemDetailFragment extends DialogFragment {


	Item currentlyDisplayedItem;

	protected AbstractItemDetailFragment() {

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
		setCancelable(true);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (shouldShowItemAfterViewCreation()) showItem();
	}

	protected abstract void showItem();

	protected boolean shouldShowItemAfterViewCreation() {
		return true;
	}

	protected Uri getUrlFromCurrentItem() {
		return PreferenceFacade.getInstance().getHostAddress(getContext())
				.buildUpon()
				.appendPath(currentlyDisplayedItem.getImage().getImageUrl())
				.build();
	}

	protected void handleUnknowFormat(@NonNull Uri videoUrl, @StringRes int message) {
		handleUnknowFormat(videoUrl.toString(), message);
	}

	protected void handleUnknowFormat(@NonNull String videoUrl, @StringRes int message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
		dismiss();
		findAlternativeApp(videoUrl);
	}

	private void findAlternativeApp(String videoUrl) {
		Uri u = Uri.parse(videoUrl);
		Intent intent = new Intent(Intent.ACTION_VIEW, u);
		intent.setDataAndType(u, "video/*");
		Intent chooser = Intent.createChooser(intent, getString(R.string.fragment_itemdetailfragment_openvideowith));

		if (intent.resolveActivity(getContext().getPackageManager()) != null) {
			startActivity(chooser);
		}
	}

	public static void showWithItem(@NonNull FragmentManager fm, String tag, @NonNull Item item) {
		AbstractItemDetailFragment fragment = ItemDetailFactory.getInstance().newInstance(item);
		if (fragment == null) {
			//TODO: findAlternativeApp();
			return;
		}
		fragment.currentlyDisplayedItem = item;
		if (fragment.getDialog() == null || !fragment.getDialog().isShowing()) {
			fragment.show(fm, tag);
		}
	}
}
