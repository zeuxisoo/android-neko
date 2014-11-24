package im.after.app;

import android.app.Application;
import android.content.res.Configuration;

import im.after.app.entity.bean.UserBean;
import im.after.app.helper.LanguageHelper;

public class AppContext extends Application {

    private static final String TAG = AppContext.class.getSimpleName();
    private UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();

        this.setLocale();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        this.setLocale();
    }

    public void setLocale() {
        LanguageHelper.setLanguage(this.getBaseContext());
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

}
