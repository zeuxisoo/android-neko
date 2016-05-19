package im.after.neko.mvp.view.login;

import android.os.Bundle;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.BindView;
import im.after.neko.R;
import im.after.neko.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.rippleLayoutLoginButton)
    View mRippleLayoutLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initContentView() {
        this.setContentView(R.layout.activity_login);
    }

    @Override
    public void initViewAndListener() {
        MaterialRippleLayout.on(this.mRippleLayoutLoginButton).create();
    }

    @Override
    public void initPresenter() {

    }

}
