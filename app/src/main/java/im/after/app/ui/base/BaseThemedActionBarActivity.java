package im.after.app.ui.base;

import android.os.Bundle;
import android.preference.PreferenceManager;

import im.after.app.R;

public class BaseThemedActionBarActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString(
            this.getString(R.string.preference_settings_theme_key),
            this.getString(R.string.preference_settings_theme_default)
        ));

        switch(theme) {
            case 1:
                this.setTheme(R.style.AppTheme_Black);
                break;
            default:
                this.setTheme(R.style.AppTheme_Dark);
                break;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.getWindow().setBackgroundDrawable(null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.invalidateOptionsMenu();
    }
}
