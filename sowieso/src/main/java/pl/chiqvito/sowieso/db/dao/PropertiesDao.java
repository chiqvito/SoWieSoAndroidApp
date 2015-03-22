package pl.chiqvito.sowieso.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.db.DBConstatants;
import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.model.PropertyEntity;

public class PropertiesDao implements DAO<PropertyEntity, String> {

    private static final String TAG = PropertiesDao.class.getName();

    private final DbManager db;

    public PropertiesDao(DbManager db) {
        this.db = db;
    }

    @Override
    public Long insert(PropertyEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_PROPERTIES_A__NAME, type.getName());
        values.put(DBConstatants.DB_TABLE_PROPERTIES_A__VALUE, type.getValue());
        return db.getDb().insert(DBConstatants.DB_TABLE_PROPERTIES, null, values);
    }

    @Override
    public void update(PropertyEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_PROPERTIES_A__NAME, type.getName());
        values.put(DBConstatants.DB_TABLE_PROPERTIES_A__VALUE, type.getValue());
        db.getDb().update(DBConstatants.DB_TABLE_PROPERTIES, values, DBConstatants.DB_TABLE_PROPERTIES_A__NAME + "=\"" + type.getName() + "\"", null);
    }

    @Override
    public void delete(PropertyEntity type) {
        db.getDb().delete(DBConstatants.DB_TABLE_PROPERTIES, DBConstatants.DB_TABLE_PROPERTIES_A__NAME + "=\"" + type.getName() + "\"", null);
    }

    @Override
    public void deleteAll() {
        Log.d(TAG, "deleteAll");
        db.getDb().execSQL("delete from " + DBConstatants.DB_TABLE_PROPERTIES);
        db.getDb().execSQL("vacuum");
    }

    @Override
    public PropertyEntity get(String id) {
        Cursor c = null;
        PropertyEntity property = null;
        try {
            c = this.db.getDb().query(true, DBConstatants.DB_TABLE_PROPERTIES, DBConstatants.DB_TABLE_PROPERTIES_COLS, DBConstatants.DB_TABLE_PROPERTIES_A__NAME + "=\"" + id + "\"", null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                property = fillProperty(c);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        Log.v(TAG, id + " " + property);
        return property;
    }

    @Override
    public List<PropertyEntity> getAll() {
        ArrayList<PropertyEntity> ret = new ArrayList<PropertyEntity>();
        Cursor c = null;
        try {
            c = this.db.getDb().query(DBConstatants.DB_TABLE_PROPERTIES, DBConstatants.DB_TABLE_PROPERTIES_COLS, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                PropertyEntity property = fillProperty(c);
                ret.add(property);
                c.moveToNext();
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return ret;
    }

    @Override
    public Integer count() {
        Cursor mcursor = db.getDb().rawQuery(DBConstatants.DB_TABLE_PROPERTIES_COUNT, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }

    private PropertyEntity fillProperty(Cursor c) {
        PropertyEntity property = new PropertyEntity();
        int idx = c.getColumnIndex(DBConstatants.DB_TABLE_PROPERTIES_A__NAME);
        property.setName(c.getString(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_PROPERTIES_A__VALUE);
        property.setValue(c.getString(idx));
        return property;
    }

}
