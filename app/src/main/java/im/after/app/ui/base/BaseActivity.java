package im.after.app.ui.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.gms.analytics.GoogleAnalytics;

import im.after.app.AppContext;
import im.after.app.AppManager;
import im.after.app.helper.AnalyticsHelper;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().addActivity(this);

        ((AppContext) getApplication()).getTracker(AppContext.AnalyticsTrackerName.APP_TRACKER);
        ((AppContext) this.getApplicationContext()).setLocale();
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AnalyticsHelper.sendScreenName(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getInstance().finishActivity(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ((AppContext) this.getApplicationContext()).setLocale();
    }

    protected String locale(int stringId) {
        return this.getResources().getString(stringId);
    }

}
