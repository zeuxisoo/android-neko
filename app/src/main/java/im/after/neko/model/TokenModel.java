package im.after.neko.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import im.after.neko.ApplicationDatabase;

@Table(database = ApplicationDatabase.class)
public class TokenModel extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    public long id;

    @Column(name = "token")
    public String token;

}
