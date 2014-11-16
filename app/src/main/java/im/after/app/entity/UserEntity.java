package im.after.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import im.after.app.helper.EncryptHelper;

@DatabaseTable(tableName = "user")
public class UserEntity extends BaseEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String username;

    @DatabaseField
    private String email;

    @DatabaseField
    @JsonProperty("hashed_username")
    private String hashed_username;

    @DatabaseField(dataType = DataType.DATE_STRING)
    @JsonProperty("create_at")
    private Date create_at;

    @DatabaseField(dataType = DataType.DATE_STRING)
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
