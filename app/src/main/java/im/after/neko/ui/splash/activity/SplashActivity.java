package im.after.neko.ui.splash.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

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

        // View
        this.mView = View.inflate(this, R.layout.activity_splash, null);
        this.setContentView(this.mView);

        // Inject
        this.mActivityComponent.inject(this);

        // Animation
        this.mSplashPresenter.beginAnimation(this.mView, new Animation.AnimationListener() {
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
    }

}