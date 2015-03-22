package pl.chiqvito.sowieso.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = DBOpenHelper.class.getSimpleName();

    public DBOpenHelper(final Context context) {
        super(context, DBConstatants.DB_NAME, null, DBConstatants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DBConstatants.DB_CREATE_PROPERTIES);
            db.execSQL(DBConstatants.DB_CREATE_BG_CATEGORIES);
            db.execSQL(DBConstatants.DB_CREATE_BG_EXPENSES);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstatants.DB_TABLE_PROPERTIES);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstatants.DB_TABLE_BG_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstatants.DB_TABLE_BG_CATEGORIES);
        onCreate(db);
    }

}
