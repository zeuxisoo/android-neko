package im.after.app.helper;

import android.app.Activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import im.after.app.AppContext;

public class AnalyticsHelper {

    public static void sendScreenName(Activity activity) {
        Tracker tracker = ((AppContext) activity.getApplication()).getTracker(AppContext.AnalyticsTrackerName.APP_TRACKER);

        tracker.setScreenName(activity.getClass().getSimpleName());
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    public static void sendScreenName(Activity activity, String fragmentName) {
        Tracker tracker = ((AppContext) activity.getApplication()).getTracker(AppContext.AnalyticsTrackerName.APP_TRACKER);

        tracker.setScreenName(activity.getClass().getSimpleName() + " - " + fragmentName);
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }


}
