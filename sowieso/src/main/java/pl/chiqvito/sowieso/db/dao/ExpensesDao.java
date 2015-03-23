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
import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ExpensesDao implements DAO<ExpenseEntity, Long> {

    private static final String TAG = ExpensesDao.class.getName();

    private final DbManager db;

    public ExpensesDao(DbManager db) {
        this.db = db;
    }

    @Override
    public Long insert(ExpenseEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__AMOUNT, type.getAmount());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__CATEGORY_ID, type.getCategoryId());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__INFO, type.getInfo());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__NAME, type.getName());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__OPERATIONDATE, type.getOperationDate());
        long id = this.db.getDb().insert(DBConstatants.DB_TABLE_BG_EXPENSES, null, values);
        type.setId(id);
        return id;
    }

    @Override
    public void update(ExpenseEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__AMOUNT, type.getAmount());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__CATEGORY_ID, type.getCategoryId());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__ID, type.getId());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__INFO, type.getInfo());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__NAME, type.getName());
        values.put(DBConstatants.DB_TABLE_BG_EXPENSES_A__OPERATIONDATE, type.getOperationDate());
        this.db.getDb().update(DBConstatants.DB_TABLE_BG_EXPENSES, values, DBConstatants.DB_TABLE_BG_EXPENSES_A__ID + "=" + type.getId(), null);
    }

    @Override
    public void delete(ExpenseEntity type) {
        this.db.getDb().delete(DBConstatants.DB_TABLE_BG_EXPENSES, DBConstatants.DB_TABLE_BG_EXPENSES_A__ID + "=" + type.getId(), null);
    }

    @Override
    public void deleteAll() {
        this.db.getDb().delete(DBConstatants.DB_TABLE_BG_EXPENSES, null, null);
        db.getDb().execSQL("vacuum");
    }

    @Override
    public ExpenseEntity get(Long id) {
        Cursor c = null;
        ExpenseEntity exp = null;
        try {
            c = this.db.getDb().query(true, DBConstatants.DB_TABLE_BG_EXPENSES, DBConstatants.DB_TABLE_BG_EXPENSES_COLS, DBConstatants.DB_TABLE_BG_EXPENSES_A__ID + "=" + id, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                exp = fillExp(c);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return exp;
    }

    @Override
    public List<ExpenseEntity> getAll() {
        ArrayList<ExpenseEntity> ret = new ArrayList<ExpenseEntity>();
        Cursor c = null;
        try {
            c = this.db.getDb().query(DBConstatants.DB_TABLE_BG_EXPENSES, DBConstatants.DB_TABLE_BG_EXPENSES_COLS, null, null, null, null, DBConstatants.DB_TABLE_BG_EXPENSES_A__ID + " DESC");
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                ExpenseEntity exp = fillExp(c);
                ret.add(exp);
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

    public List<ExpenseEntity> getAllWithCategories() {
        ArrayList<ExpenseEntity> ret = new ArrayList<ExpenseEntity>();
        Cursor c = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__ID + ",");
            sql.append("e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__NAME + ",");
            sql.append("e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__AMOUNT + ",");
            sql.append("e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__CATEGORY_ID + ",");
            sql.append("e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__INFO + ",");
            sql.append("e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__OPERATIONDATE + ",");
            sql.append("c." + DBConstatants.DB_TABLE_BG_CATEGORIES_A__NAME + " as categoryname");
            sql.append(" from " + DBConstatants.DB_TABLE_BG_EXPENSES + " e");
            sql.append(" join " + DBConstatants.DB_TABLE_BG_CATEGORIES + " c");
            sql.append(" on e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__CATEGORY_ID + "=c." + DBConstatants.DB_TABLE_BG_CATEGORIES_A__ID);
            sql.append(" order by e." + DBConstatants.DB_TABLE_BG_EXPENSES_A__ID + " DESC");

            c = this.db.getDb().rawQuery(sql.toString(), null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                ExpenseEntity exp = fillExp(c);
                CategoryEntity cat = new CategoryEntity();
                cat.setId(exp.getCategoryId());
                int idx = c.getColumnIndex("categoryname");
                cat.setName(c.getString(idx));
                exp.setCategory(cat);
                ret.add(exp);
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
        Cursor mcursor = db.getDb().rawQuery(DBConstatants.DB_TABLE_EXPENSES_COUNT, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }

    private ExpenseEntity fillExp(Cursor c) {
        ExpenseEntity exp = new ExpenseEntity();
        int idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_EXPENSES_A__ID);
        exp.setId(c.getLong(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_EXPENSES_A__NAME);
        exp.setName(c.getString(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_EXPENSES_A__AMOUNT);
        exp.setAmount(c.getString(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_EXPENSES_A__CATEGORY_ID);
        exp.setCategoryId(c.getLong(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_EXPENSES_A__INFO);
        exp.setInfo(c.getString(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_BG_EXPENSES_A__OPERATIONDATE);
        exp.setOperationDate(c.getString(idx));
        return exp;
    }

}
