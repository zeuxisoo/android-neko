package im.after.app.ui.base;

import android.app.Activity;
import android.os.Bundle;

import im.after.app.AppManager;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getInstance().finishActivity(this);
    }

    protected String locale(int stringId) {
        return this.getResources().getString(stringId);
    }

}