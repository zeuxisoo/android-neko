package im.after.app.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.app.helper.MaterialDialogHelper;

@Module
public class HelperModule {

    @Provides
    @Singleton
    public MaterialDialogHelper provideMaterialDialogHelper() {
        return new MaterialDialogHelper();
    }

}
