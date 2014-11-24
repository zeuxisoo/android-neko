package im.after.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import im.after.app.R;
import im.after.app.helper.LanguageHelper;
import im.after.app.ui.base.BaseActionBarActivity;

public class SettingsActivity extends BaseActionBarActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_settings);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);

        this.getFragmentManager()
            .beginTransaction()
            .replace(R.id.frameLayoutSettingsFragment, new SettingsFragment())
            .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            this.addPreferencesFromResource(R.xml.preference_settings);

            this.setUpLanguagePreference();
        }

        private void setUpLanguagePreference() {
            String key = this.getString(R.string.preference_settings_locale_key);

            // Set summary
            ListPreference listPreference = (ListPreference) this.findPreference(key);
            listPreference.setSummary(String.format(this.getString(R.string.preference_settings_locale_summary), LanguageHelper.getCurrentLanguage()));

            // Change event
            listPreference.setOnPreferenceChangeListener((Preference preference, Object newValue) -> {
                preference.setSummary(String.format(this.getString(R.string.preference_settings_locale_summary), newValue.toString()));

                // Switch language
                LanguageHelper.setLanguage(this.getActivity().getBaseContext());

                // Restart to enable new locale
                Activity activity = this.getActivity();

                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                activity.finish();
                activity.startActivity(intent);

                return true;
            });
        }

    }

}
