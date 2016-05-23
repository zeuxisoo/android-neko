package im.after.neko.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import im.after.neko.ApplicationManager;
import im.after.neko.MyApplication;
import im.after.neko.di.component.ActivityComponent;
import im.after.neko.di.component.DaggerActivityComponent;
import im.after.neko.di.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public ActivityComponent mActivityComponent;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initLogger();

        this.initComponent();

        this.initContentView();
        this.initButterKnife();
        this.initInjector();
        this.initPresenter();
        this.initToolbar();
        this.initViewAndListener();

        this.setStatusBarTranslucency();

        ApplicationManager.getApplicationManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.mUnbinder.unbind();

        ApplicationManager.getApplicationManager().finishActivity(this);
    }

    // Init methods
    protected void initLogger() {
        Logger.init("Neko").methodCount(1).hideThreadInfo();
    }

    protected void initComponent() {
        MyApplication myApplication = (MyApplication) this.getApplication();

        this.mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(myApplication.getApplicationComponent())
                .build();
    }

    protected void initButterKnife() {
        this.mUnbinder = ButterKnife.bind(this);
    }

    //
    protected void setStatusBarTranslucency() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();

            final int translucentStatusFlag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

            if (this.isApplyStatusBarTranslucency()) {
                layoutParams.flags |= translucentStatusFlag;
            }else{
                layoutParams.flags &= ~translucentStatusFlag;
            }

            window.setAttributes(layoutParams);
        }
    }

    abstract public void initInjector();
    abstract public void initContentView();
    abstract public void initViewAndListener();
    abstract public void initPresenter();
    abstract public void initToolbar();

    abstract public boolean isApplyStatusBarTranslucency();

}
