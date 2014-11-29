package im.after.app;

import android.app.Application;
import android.content.res.Configuration;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import im.after.app.entity.bean.UserBean;
import im.after.app.helper.LanguageHelper;

public class AppContext extends Application {

    private static final String TAG = AppContext.class.getSimpleName();
    private static final String PROPERTY_ID = "UA-57199957-1";

    private UserBean userBean;

    private HashMap<AnalyticsTrackerName, Tracker> analyticsTrackers = new HashMap<AnalyticsTrackerName, Tracker>();
    public enum AnalyticsTrackerName {
        GLOBAL_TRACKER,
        APP_TRACKER,
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.setLocale();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        this.setLocale();
    }

    public void setLocale() {
        LanguageHelper.setLanguage(this.getBaseContext());
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public synchronized Tracker getTracker(AnalyticsTrackerName trackerName) {
        if (!this.analyticsTrackers.containsKey(trackerName)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

            // Only one checker
            Tracker tracker;
            if (trackerName == AnalyticsTrackerName.GLOBAL_TRACKER) {
                tracker = analytics.newTracker(PROPERTY_ID);
            }else{
                tracker = analytics.newTracker(R.xml.analytics_app_tracker);
            }

            this.analyticsTrackers.put(trackerName, tracker);
        }
        return this.analyticsTrackers.get(trackerName);
    }

}
