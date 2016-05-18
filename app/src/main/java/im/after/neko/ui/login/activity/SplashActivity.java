package im.after.neko.ui.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import im.after.neko.R;
import im.after.neko.ui.login.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity {

    @Inject
    SplashPresenter mSplashPresenter;

    @BindView(R.id.imageViewSplashBackground)
    ImageView splashBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_splash);
    }

}