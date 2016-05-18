package im.after.neko.ui.splash.presenter;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import javax.inject.Inject;

import im.after.neko.ui.splash.contract.SplashContract;

public class SplashPresenter implements SplashContract {

    AlphaAnimation mAlphaAnimation;

    @Inject
    public SplashPresenter() {

    }

    @Override
    public void beginAnimation(View view, Animation.AnimationListener callback) {
        this.mAlphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        this.mAlphaAnimation.setDuration(2000);
        this.mAlphaAnimation.setAnimationListener(callback);

        view.startAnimation(this.mAlphaAnimation);
    }

}
