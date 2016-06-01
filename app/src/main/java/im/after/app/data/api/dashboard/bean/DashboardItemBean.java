package im.after.app.data.api.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import im.after.app.base.BaseBean;

public class DashboardItemBean extends BaseBean {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("user")
    @Expose
    private UserBean user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}
