package im.after.neko.api.auth.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthErrorBean {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public AuthErrorBean() {
    }

    public AuthErrorBean(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}