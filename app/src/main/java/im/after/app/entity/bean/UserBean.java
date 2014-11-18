package im.after.app.entity.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import im.after.app.entity.BaseEntity;
import im.after.app.helper.EncryptHelper;

public class UserBean extends BaseEntity {

    private int id;
    private String username;
    private String email;

    @JsonProperty("hashed_username")
    private String hashed_username;

    @JsonProperty("create_at")
    private Date create_at;

    @JsonProperty("update_at")
    private Date update_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedUsername() {
        return hashed_username;
    }

    public void setHashedUsername(String hashed_username) {
        this.hashed_username = hashed_username;
    }

    public Date getCreateAt() {
        return create_at;
    }

    public void setCreateAt(Date create_at) {
        this.create_at = create_at;
    }

    public Date getUpdateAt() {
        return update_at;
    }

    public void setUpdateAt(Date update_at) {
        this.update_at = update_at;
    }

    public String getAvatar() {
        return "http://www.gravatar.com/avatar/" + EncryptHelper.md5(this.getEmail());
    }

}
