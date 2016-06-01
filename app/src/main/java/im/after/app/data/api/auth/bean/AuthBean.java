package im.after.app.data.api.auth.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import im.after.app.base.BaseBean;

public class AuthBean extends BaseBean {

    @SerializedName("token")
    @Expose
    private String token;

    public AuthBean() {
    }

    public AuthBean(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}