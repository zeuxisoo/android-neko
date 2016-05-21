package im.after.neko.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
        this.initViewAndListener();

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

    abstract public void initInjector();
    abstract public void initContentView();
    abstract public void initViewAndListener();
    abstract public void initPresenter();

}
