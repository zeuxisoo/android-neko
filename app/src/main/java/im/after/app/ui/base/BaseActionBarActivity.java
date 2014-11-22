package im.after.app.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import im.after.app.AppManager;

public class BaseActionBarActivity extends ActionBarActivity {

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

}
