package im.after.app.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import im.after.app.AppManager;
import im.after.app.AppStart;
import im.after.app.R;
import im.after.app.helper.LanguageHelper;
import im.after.app.ui.base.BaseThemedActionBarActivity;

public class SettingsActivity extends BaseThemedActionBarActivity {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.close();
    }

    private void close() {
        this.finish();
        this.overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            this.addPreferencesFromResource(R.xml.preference_settings);

            this.setUpLanguagePreference();
            this.setUpThemePreference();
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

                // Finish all activity
                AppManager.getInstance().finishAllActivity();

                // Restart app
                Intent intentForRestart = new Intent(this.getActivity(), AppStart.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this.getActivity(), 123456, intentForRestart, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager)this.getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

                return true;
            });
        }

        private void setUpThemePreference() {
            String key = this.getString(R.string.preference_settings_theme_key);

            ListPreference listPreference = (ListPreference) this.findPreference(key);

            listPreference.setOnPreferenceChangeListener((Preference preference, Object newValue) -> {
                // Finish all activity
                AppManager.getInstance().finishAllActivity();

                // Restart app
                Intent intentForRestart = new Intent(this.getActivity(), AppStart.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this.getActivity(), 123456, intentForRestart, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager)this.getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

                return true;
            });
        }

    }

}
