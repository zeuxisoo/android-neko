package im.after.app.ui.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import im.after.app.AppContext;
import im.after.app.AppManager;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().addActivity(this);

        ((AppContext) this.getApplicationContext()).setLocale();
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
