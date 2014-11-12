package im.after.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class AppManager {

    private static AppManager instance;
    private static Stack<Activity> activityStack;

    public AppManager() {
    }

    public synchronized static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishAllActivity() {
        for(Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    public void exitApp(Context context) {
        try {
            finishAllActivity();

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            activityManager.killBackgroundProcesses(context.getPackageName());

            System.exit(0);
        } catch (Exception e) {
        }
    }

}
