package im.after.app.ui;

import android.app.Activity;
import android.os.Bundle;

import im.after.app.AppManager;
import im.after.app.helper.UIHelper;

public class BaseActivity extends Activity {

    protected UIHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.uiHelper = new UIHelper(this);

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
