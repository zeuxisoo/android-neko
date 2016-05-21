package im.after.neko.data.db.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import im.after.neko.data.db.ApplicationDatabase;

@Table(database = ApplicationDatabase.class)
public class TokenModel extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private long id;

    @Column(name = "token")
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
