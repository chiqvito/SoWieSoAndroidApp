package pl.chiqvito.sowieso.db;

import android.database.sqlite.SQLiteDatabase;

public interface DbManager {

    SQLiteDatabase getDb();

    void openDb();

    void closeDb();

    void resetDb();

}
