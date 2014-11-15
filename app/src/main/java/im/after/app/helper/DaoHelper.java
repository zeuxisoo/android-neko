package im.after.app.helper;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

public class DaoHelper<T> {

    private final String TAG = this.getClass().getSimpleName();
    private static SqliteOpenHelper db;
    private Dao dao;

    public DaoHelper(Context context, Class<T> object) {
        db = new SqliteOpenHelper(context);

        try {
            this.dao = db.getDao(object);
        }catch(SQLException e) {
            Log.d(TAG, "Error in DaoHelper::constructor", e);
            throw new RuntimeException(e);
        }
    }

    // Create
    public boolean create(T object) {
        try {
            int state = this.dao.create(object);
            return state > 0;
        }catch(SQLException e) {
            Log.d(TAG, "Error in DaoHelper::create", e);
        }
        return false;
    }

    // Read
    public List<T> find(PreparedQuery<T> preparedQuery) {
        List<T> items = null;

        try {
            items = this.dao.query(preparedQuery);
        }catch(SQLException e) {
            Log.d(TAG, "Error in DaoHelper::find", e);
        }

        return items;
    }

    public List<T> findAll() {
        List<T> all = null;

        try {
            all = this.dao.queryForAll();
        }catch(SQLException e) {
            Log.d(TAG, "Error in DaoHelper::findAll", e);
        }

        return all;
    }

    public T findFirst() {
        T item = null;

        try {
            List<T> items = this.find(this.dao.queryBuilder().offset(0L).limit(1L).prepare());

            if (!items.isEmpty()) {
                item = items.get(0);
            }
        }catch(SQLException e) {
            Log.d(TAG, "Error in DaoHelper::findFirst", e);
        }

        return item;
    }

    // Update

    // Delete
    public boolean deleteAll() {
        try {
            int state = this.dao.delete(this.findAll());
            return state > 0;
        }catch(SQLException e) {
            Log.d(TAG, "Error in DaoHelper::deleteAll", e);
        }
        return false;
    }

}
