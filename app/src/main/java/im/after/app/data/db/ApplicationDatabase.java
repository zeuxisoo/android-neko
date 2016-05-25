package im.after.app.data.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = ApplicationDatabase.NAME, version = ApplicationDatabase.VERSION)
public class ApplicationDatabase {

    public static final String NAME = "neko";

    public static final int VERSION = 1;

}
