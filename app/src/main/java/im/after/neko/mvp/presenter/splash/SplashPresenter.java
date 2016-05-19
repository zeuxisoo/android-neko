package im.after.neko.mvp.presenter.splash;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import javax.inject.Inject;

import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.mvp.view.splash.SplashActivity;
import im.after.neko.mvp.contract.splash.SplashContract;

public class SplashPresenter extends BasePresenter implements SplashContract {

    AlphaAnimation mAlphaAnimation;
    SplashActivity mSplashActivity;

    @Inject
    public SplashPresenter() {

    }

    @Override
    public void beginAnimation(View view) {
        this.mAlphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        this.mAlphaAnimation.setDuration(2000);
        this.mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("SplashActivity", "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("SplashActivity", "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("SplashActivity", "onAnimationRepeat");
            }
        });

        view.startAnimation(this.mAlphaAnimation);
    }

    @Override
    public void attachView(BaseView view) {
        this.mSplashActivity = (SplashActivity) view;
    }

    @Override
    public void detachView() {
        this.mSplashActivity = null;
    }

}
