package im.after.app.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import im.after.app.AppConfig;
import im.after.app.entity.table.LoginTable;

public class SqliteOpenHelper extends OrmLiteSqliteOpenHelper {

    private final String TAG  = this.getClass().getSimpleName();
    private Class<?> objects[] = new Class<?>[] { LoginTable.class };

    public SqliteOpenHelper(Context context) {
        super(context, FileHelper.databaseFilePath(), null, AppConfig.Database.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for(Class<?> object : this.objects) {
                TableUtils.createTableIfNotExists(connectionSource, object);
            }
        }catch(SQLException e) {
            Log.d(TAG, "Error in SqliteOpenHelper::onCreate", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for(Class<?> object : this.objects) {
                TableUtils.dropTable(connectionSource, object, true);
            }
        }catch(SQLException e) {
            Log.d(TAG, "Error in SqliteOpenHelper::onUpgrade when %s to %s version".format(String.valueOf(oldVersion), String.valueOf(newVersion)), e);
            throw new RuntimeException(e);
        }
    }

}
