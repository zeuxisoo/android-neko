package im.after.neko.presenter.splash;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import javax.inject.Inject;

import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.view.splash.SplashActivity;
import im.after.neko.contract.splash.SplashContract;

public class SplashPresenter extends BasePresenter implements SplashContract {

    private AlphaAnimation mAlphaAnimation;
    private SplashActivity mSplashActivity;

    @Inject
    public SplashPresenter() {

    }

    // Implementation for BasePresenter
    @Override
    public void attachView(BaseView view) {
        this.mSplashActivity = (SplashActivity) view;
    }

    @Override
    public void detachView() {
        this.mSplashActivity = null;
    }

    // Implementation for SplashContract
    @Override
    public void beginAnimation(View view) {
        this.mAlphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        this.mAlphaAnimation.setDuration(2000);
        this.mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                triggerOnAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(this.mAlphaAnimation);
    }

    //
    public void triggerOnAnimationEnd() {
        this.mSplashActivity.redirectToLoginActivity();
    }

}
