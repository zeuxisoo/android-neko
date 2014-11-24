package im.after.app.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

import im.after.app.R;

public class LanguageHelper {

    private static final String TAG = "LanguageHelper";

    public static void setLanguage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String language = sharedPreferences.getString(context.getString(R.string.preference_settings_locale_key), "");

        Locale locale;
        if (language.equals("")) {
            language                    = Locale.getDefault().getLanguage();
            String availableLanguages[] = context.getResources().getStringArray(R.array.preference_language_values);

            for (String availableLanguage : availableLanguages) {
                if (availableLanguage.equals(language)) {
                    return;
                }
            }

            locale = getLocale(context.getResources().getString(R.string.preference_settings_locale_default));
        }else{
            locale = getLocale(language);
        }

        // Fix zh multi language :(
        if (language.equals("zh_CN")) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }else if (language.equals("zh_TW")) {
            locale = Locale.TRADITIONAL_CHINESE;
        }

        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;

        Resources resource = context.getApplicationContext().getResources();

        resource.updateConfiguration(configuration, resource.getDisplayMetrics());
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    private static Locale getLocale(String language) {
        final int idx = language.indexOf('-');

        if (idx != -1) {
            final String[] parts = language.split("-");

            return new Locale(parts[0], parts[1].substring(1));
        }else{
            return new Locale(language);
        }
    }

}
