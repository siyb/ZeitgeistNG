package com.sphericalelephant.zeitgeistng.fragment.preference;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.sphericalelephant.zeitgeistng.R;

import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
	private UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
	private IntegerValidator integerValidator = new IntegerValidator();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.general_preferences);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		boolean valid = isValid(preference, newValue);
		if (!valid)
			Toast.makeText(getActivity(), R.string.fragment_settingsfragment_invalidvalue, Toast.LENGTH_SHORT).show();
		return valid;
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
				return false;
		}
	}

}
