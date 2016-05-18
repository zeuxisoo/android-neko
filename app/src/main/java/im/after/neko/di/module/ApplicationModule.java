package im.after.neko.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import im.after.neko.MyApplication;

@Module
public class ApplicationModule {

    private MyApplication mMyApplication;

    public ApplicationModule(MyApplication application) {
        this.mMyApplication = application;
    }

    @Provides
    public Context getApplicationContext() {
        return this.mMyApplication;
    }

}
