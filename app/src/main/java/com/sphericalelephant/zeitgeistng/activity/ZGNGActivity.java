package com.sphericalelephant.zeitgeistng.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.fragment.imagegrid.ImageGridFragment;

import at.diamonddogs.ui.annotation.UiAnnotationProcessor;
import at.diamonddogs.ui.receiver.IndeterminateProgressControl;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ZGNGActivity extends AppCompatActivity implements IndeterminateProgressControl {
	@Bind(R.id.activity_zgngactivity_pb_progress)
	ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zgngactivity);

		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.activity_zgngactivity_fl_container, ImageGridFragment.newInstance())
				.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ButterKnife.bind(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ButterKnife.unbind(this);
	}

	@Override
	public void showIndeterminateProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideIndeterminateProgress() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean isIndeterminateProgressShowing() {
		return progressBar.getVisibility() == View.VISIBLE;
	}

	@Override
	public void processUiAnnotations(UiAnnotationProcessor uiAnnotationProcessor) throws IllegalAccessException, IllegalArgumentException {
		uiAnnotationProcessor.process(this);
	}
}
