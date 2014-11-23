package im.after.app.entity.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import im.after.app.entity.BaseEntity;

public class ArticleItemBean extends BaseEntity {

    private int id;
    private int user_id;
    private String username;
    private String title;
    private String content;

    @JsonProperty("create_at")
    private Date createAt;

    @JsonProperty("update_at")
    private Date updateAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateAtHumanTime() {
        PrettyTime p = new PrettyTime();

        return p.format(this.getCreateAt());
    }
}
