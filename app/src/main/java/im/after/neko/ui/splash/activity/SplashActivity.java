package im.after.neko.ui.splash.activity;

import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import im.after.neko.R;
import im.after.neko.base.BaseActivity;
import im.after.neko.ui.splash.presenter.SplashPresenter;

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
        this.mSplashPresenter.beginAnimation(this.mView);
    }

    @Override
    public void initPresenter() {
        this.mSplashPresenter.attachView(this);
    }

}