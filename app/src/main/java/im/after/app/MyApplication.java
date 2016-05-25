package im.after.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import im.after.app.di.component.ApplicationComponent;
import im.after.app.di.component.DaggerApplicationComponent;
import im.after.app.di.module.ApplicationModule;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initComponent();
        this.initDatabase();
    }

    private void initComponent() {
        this.mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initDatabase() {
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public ApplicationComponent getApplicationComponent() {
        return this.mApplicationComponent;
    }

}
