package im.after.neko.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.after.neko.data.api.auth.AuthApi;
import im.after.neko.model.login.LoginModel;

@Module
public class ModelModule {

    @Provides
    @Singleton
    public LoginModel provideLoginModel(AuthApi authApi) {
        return new LoginModel(authApi);
    }

}
