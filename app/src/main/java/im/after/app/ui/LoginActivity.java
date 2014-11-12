package im.after.app.ui;

import android.os.Bundle;

import im.after.app.R;


public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().hide();
        this.getActionBar().setDisplayUseLogoEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        this.setContentView(R.layout.activity_login);
    }

}
