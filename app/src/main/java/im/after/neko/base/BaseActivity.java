package im.after.neko.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import im.after.neko.ApplicationManager;
import im.after.neko.MyApplication;
import im.after.neko.di.component.ActivityComponent;
import im.after.neko.di.component.DaggerActivityComponent;
import im.after.neko.di.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

    public ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initComponent();

        ApplicationManager.getApplicationManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ApplicationManager.getApplicationManager().finishActivity(this);
    }

    public void initComponent() {
        MyApplication myApplication = (MyApplication) this.getApplication();

        this.mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(myApplication.getApplicationComponent())
                .build();
    }

}
