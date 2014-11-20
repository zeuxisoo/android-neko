package im.after.app.ui;

import android.os.Bundle;

import im.after.app.R;

public class ComposeActivity extends BaseActivity {

    private static final String TAG = "ComposeActivity";

    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_MEMO    = 2;
    public static final int TYPE_TALK    = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

}
