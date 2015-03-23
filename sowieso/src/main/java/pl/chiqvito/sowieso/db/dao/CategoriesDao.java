package pl.chiqvito.sowieso.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.db.DBConstatants;
import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.model.CategoryEntity;

public class CategoriesDao implements DAO<CategoryEntity, Long> {

    private static final String TAG = CategoriesDao.class.getName();

    private final DbManager db;

    public CategoriesDao(DbManager db) {
        this.db = db;
    }

    @Override
    public Long insert(CategoryEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID, type.getId());
        values.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__NAME, type.getName());
        values.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__PARENT_ID, type.getParentId());
        return this.db.getDb().insert(DBConstatants.DB_TABLE_BG_CATEGORIES, null, values);
    }

    @Override
    public int update(CategoryEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID, type.getId());
        values.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__NAME, type.getName());
        values.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__PARENT_ID, type.getParentId());
        return this.db.getDb().update(DBConstatants.DB_TABLE_BG_CATEGORIES, values, DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID + "=" + type.getId(), null);
    }

    @Override
    public int delete(CategoryEntity type) {
        return this.db.getDb().delete(DBConstatants.DB_TABLE_BG_CATEGORIES, DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID + "=" + type.getId(), null);
    }

    @Override
    public int deleteAll() {
        int rows = this.db.getDb().delete(DBConstatants.DB_TABLE_BG_CATEGORIES, null, null);
        db.getDb().execSQL("vacuum");
        return rows;
    }

    @Override
    public CategoryEntity get(Long id) {
        Cursor c = null;
        CategoryEntity category = null;
        try {
            c = this.db.getDb().query(true, DBConstatants.DB_TABLE_BG_CATEGORIES, DBConstatants.DB_TABLE_BG_CATEGORIES_COLS, DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID + "=" + id, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                category = fillCategory(c);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return category;
    }

    @Override
    public List<CategoryEntity> getAll() {
        ArrayList<CategoryEntity> ret = new ArrayList<CategoryEntity>();
        Cursor c = null;
        try {
            c = this.db.getDb().query(DBConstatants.DB_TABLE_BG_CATEGORIES, DBConstatants.DB_TABLE_BG_CATEGORIES_COLS, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                CategoryEntity category = fillCategory(c);
                ret.add(category);
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
        Cursor mcursor = db.getDb().rawQuery(DBConstatants.DB_TABLE_CATEGORIES_COUNT, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }

    public List<CategoryEntity> getMainCategories() {
        ArrayList<CategoryEntity> ret = new ArrayList<CategoryEntity>();
        Cursor c = null;
        try {
            c = this.db.getDb().query(DBConstatants.DB_TABLE_BG_CATEGORIES, DBConstatants.DB_TABLE_BG_CATEGORIES_COLS, DBConstatants.DB_TABLE_BG_CATEGORIES_A__PARENT_ID + " IS NULL", null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                CategoryEntity category = fillCategory(c);
                ret.add(category);
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

    public List<CategoryEntity> getSubCategories(long parentId) {
        ArrayList<CategoryEntity> ret = new ArrayList<CategoryEntity>();
        Cursor c = null;
        try {
            c = this.db.getDb().query(DBConstatants.DB_TABLE_BG_CATEGORIES, DBConstatants.DB_TABLE_BG_CATEGORIES_COLS, DBConstatants.DB_TABLE_BG_CATEGORIES_A__PARENT_ID + "=" + parentId, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                CategoryEntity category = fillCategory(c);
                ret.add(category);
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

    public void setSelected(long id) {
        ContentValues args = new ContentValues();
        args.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__SELECTED, 0);
        this.db.getDb().update(DBConstatants.DB_TABLE_BG_CATEGORIES, args, null, null);
        args = new ContentValues();
        args.put(DBConstatants.DB_TABLE_BG_CATEGORIES_A__SELECTED, 1);
        this.db.getDb().update(DBConstatants.DB_TABLE_BG_CATEGORIES, args, DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID + "=" + id, null);
    }

    private CategoryEntity fillCategory(Cursor c) {
        CategoryEntity category = new CategoryEntity();
        int idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID);
        category.setId(c.getLong(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_CATEGORIES_A__NAME);
        category.setName(c.getString(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_CATEGORIES_A__PARENT_ID);
        Long pid = c.getLong(idx);
        if (pid.longValue() == 0)
            pid = null;
        category.setParentId(pid);
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_CATEGORIES_A__SELECTED);
        category.setIsSelected(c.getInt(idx) == 1);
        return category;
    }

}
