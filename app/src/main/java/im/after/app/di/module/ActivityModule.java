package im.after.app.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import im.after.app.di.scope.ActivityScope;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity getActivity() {
        return this.mActivity;
    }

}
