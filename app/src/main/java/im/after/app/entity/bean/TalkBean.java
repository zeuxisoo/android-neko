package im.after.app.entity.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import im.after.app.entity.BaseEntity;

public class TalkBean extends BaseEntity {

    private int id;

    @JsonProperty("user_id")
    private int userId;

    private String username;
    private String service;
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

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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
}
