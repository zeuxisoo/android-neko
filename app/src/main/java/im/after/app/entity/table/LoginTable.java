package im.after.app.entity.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import im.after.app.entity.BaseEntity;

@DatabaseTable(tableName = "login")
public class LoginTable extends BaseEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String account;

    @DatabaseField
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

}