package im.after.neko;

import android.app.Application;

import im.after.neko.di.component.ApplicationComponent;
import im.after.neko.di.component.DaggerApplicationComponent;
import im.after.neko.di.module.ApplicationModule;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initComponent();
    }

    private void initComponent() {
        this.mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

}
