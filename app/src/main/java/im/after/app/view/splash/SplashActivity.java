package im.after.app.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.presenter.splash.SplashPresenter;
import im.after.app.service.network.NetworkStateService;
import im.after.app.view.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    View mView;

    @Inject
    SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        this.mSplashPresenter.detachView();

        super.onDestroy();
    }

    @Override
    public void initInjector() {
        this.mActivityComponent.inject(this);
    }

    @Override
    public void initContentView() {
        this.mView = View.inflate(this, R.layout.activity_splash, null);
        this.setContentView(this.mView);
    }

    @Override
    public void initViewAndListener() {
        // Service
        Intent intent = new Intent(this, NetworkStateService.class);
        this.startService(intent);

        // Animation
        this.mSplashPresenter.beginAnimation(this.mView);
    }

    @Override
    public void initPresenter() {
        this.mSplashPresenter.attachView(this);
    }

    @Override
    public void initToolbar() {

    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return true;
    }

    public void redirectToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }

}