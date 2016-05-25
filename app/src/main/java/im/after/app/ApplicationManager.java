package im.after.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class ApplicationManager {

    private static ApplicationManager instance;
    private static Stack<Activity> activityStack;

    public ApplicationManager() {
    }

    public synchronized static ApplicationManager getApplicationManager() {
        if (instance == null) {
            instance = new ApplicationManager();
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
