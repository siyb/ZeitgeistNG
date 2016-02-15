package com.sphericalelephant.zeitgeistng.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.fragment.imagegrid.ImageGridFragment;

public class ZGNGActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zgngactivity);

		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.activity_zgngactivity_fl_container, ImageGridFragment.newInstance())
				.commit();
	}
}
