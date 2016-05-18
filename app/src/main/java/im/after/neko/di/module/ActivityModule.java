package im.after.neko.di.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import im.after.neko.di.scope.ActivityScope;
import im.after.neko.di.scope.ContextLife;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    @ContextLife("Activity")
    public Context provideContext() {
        return this.mActivity;
    }

}
