package com.sphericalelephant.zeitgeistng.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.fragment.imagegrid.ImageGridFragment;

import at.diamonddogs.ui.annotation.UiAnnotationProcessor;
import at.diamonddogs.ui.receiver.IndeterminateProgressControl;

public class ZGNGActivity extends AppCompatActivity implements IndeterminateProgressControl {
	private ProgressBar progressBar;
	private DrawerLayout drawerLayout;

	private ActionBarDrawerToggle drawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zgngactivity);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_zgngactivity_tb_tabs);
		setSupportActionBar(toolbar);

		drawerLayout = (DrawerLayout) findViewById(R.id.activity_zgngactivity_dl_drawerlayout);
		progressBar = (ProgressBar) findViewById(R.id.activity_zgngactivity_pb_progress);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.general_openhamburger, R.string.general_closehamburger);

		drawerLayout.setDrawerListener(drawerToggle);
		
		ActionBar ab = getSupportActionBar();

		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setHomeButtonEnabled(true);
		}

		if (getSupportFragmentManager().findFragmentById(R.id.activity_zgngactivity_fl_container) == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.activity_zgngactivity_fl_container, ImageGridFragment.newInstance())
					.commit();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
