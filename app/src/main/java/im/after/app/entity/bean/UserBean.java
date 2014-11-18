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
    private String hashedUsername;

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
        return hashedUsername;
    }

    public void setHashedUsername(String hashed_username) {
        this.hashedUsername = hashed_username;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date create_at) {
        this.createAt = create_at;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date update_at) {
        this.updateAt = update_at;
    }

    public String getAvatar() {
        return "http://www.gravatar.com/avatar/" + EncryptHelper.md5(this.getEmail());
    }

}
