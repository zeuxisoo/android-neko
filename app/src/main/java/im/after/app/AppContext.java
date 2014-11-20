package im.after.app;

import android.app.Application;

import im.after.app.entity.bean.UserBean;

public class AppContext extends Application {

    private UserBean userBean;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

}
