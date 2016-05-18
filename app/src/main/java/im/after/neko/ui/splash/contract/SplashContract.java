package im.after.neko.ui.splash.contract;

import android.view.View;
import android.view.animation.Animation;

public interface SplashContract {

    void beginAnimation(View view, Animation.AnimationListener callback);

}
