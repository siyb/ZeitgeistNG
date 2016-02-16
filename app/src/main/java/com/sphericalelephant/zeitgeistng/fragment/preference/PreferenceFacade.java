package com.sphericalelephant.zeitgeistng.fragment.preference;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class PreferenceFacade {

	 static final String KEY_ITEMSPERPAGE = "com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFragment.ITEMSPERPAGE";
	 static final String KEY_COLUMNS = "com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFragment.COLUMNS";
	 static final String KEY_URL = "com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFragment.URL";

	private static final String DEFAULT_ZEITGEIST_URL = "http://zeitgeist.li/";
	private static final int DEFAULT_ITEMSPERPAGE = 100;
	private static final int DEFAUL_COLUMNS = 100;

	private static PreferenceFacade INSTANCE;

	private PreferenceFacade() {

	}

	@NonNull
	public synchronized static PreferenceFacade getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PreferenceFacade();
		}
		return INSTANCE;
	}


	public Uri getHostAddress(Context c) {
		return Uri.parse(PreferenceManager.getDefaultSharedPreferences(c).getString(KEY_URL, DEFAULT_ZEITGEIST_URL));
	}

	public int getItemsPerPage(Context c) {
		return PreferenceManager.getDefaultSharedPreferences(c).getInt(KEY_ITEMSPERPAGE, DEFAULT_ITEMSPERPAGE);
	}

	public int getColumns(Context c) {
		return PreferenceManager.getDefaultSharedPreferences(c).getInt(KEY_COLUMNS, DEFAUL_COLUMNS);
	}
}