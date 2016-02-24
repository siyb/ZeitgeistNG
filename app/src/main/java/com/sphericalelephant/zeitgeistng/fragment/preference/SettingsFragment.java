package com.sphericalelephant.zeitgeistng.fragment.preference;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.preference.PreferenceFragment;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sphericalelephant.zeitgeistng.R;

import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
	private UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
	private IntegerValidator integerValidator = new IntegerValidator();

	public static SettingsFragment newInstance() {
		return new SettingsFragment();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.general_preferences);
		findPreference(PreferenceFacade.KEY_LOGIN).setOnPreferenceClickListener(this);
		findPreference(PreferenceFacade.KEY_ITEMSPERPAGE).setOnPreferenceChangeListener(this);
		findPreference(PreferenceFacade.KEY_COLUMNS).setOnPreferenceChangeListener(this);
		findPreference(PreferenceFacade.KEY_LOGIN).setOnPreferenceChangeListener(this);
		findPreference(PreferenceFacade.KEY_URL).setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		boolean valid = isValid(preference, newValue);
		if (!valid)
			Toast.makeText(getActivity(), R.string.fragment_settingsfragment_invalidvalue, Toast.LENGTH_SHORT).show();
		return valid;
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals(PreferenceFacade.KEY_LOGIN)) {
			IntentIntegrator integrator = new IntentIntegrator(getActivity());
			integrator.setPrompt(getString(R.string.fragment_settingsfragment_scanaccesscode));
			integrator.setBeepEnabled(false);
			integrator.setBarcodeImageEnabled(true);
			integrator.initiateScan();
			return true;
		}
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (result != null) {
			if (result.getContents() == null) {
				Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private boolean isValid(Preference preference, Object newValue) {
		switch (preference.getKey()) {
			case PreferenceFacade.KEY_COLUMNS:
				return integerValidator.isValid(newValue.toString());
			case PreferenceFacade.KEY_ITEMSPERPAGE:
				return integerValidator.isValid(newValue.toString());
			case PreferenceFacade.KEY_URL:
				return urlValidator.isValid(newValue.toString());
			default:
				return true;
		}
	}
}
