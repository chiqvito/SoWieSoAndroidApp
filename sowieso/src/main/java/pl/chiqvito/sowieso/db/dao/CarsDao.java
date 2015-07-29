package pl.chiqvito.sowieso.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.db.DBConstatants;
import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.model.CarEntity;

public class CarsDao implements DAO<CarEntity, Long> {

    private static final String TAG = CarsDao.class.getName();

    private final DbManager db;

    public CarsDao(DbManager db) {
        this.db = db;
    }

    @Override
    public Long insert(CarEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_I_CARS_A__ID, type.getId());
        values.put(DBConstatants.DB_TABLE_I_CARS_A__NAME, type.getName());
        return this.db.getDb().insert(DBConstatants.DB_TABLE_I_CARS, null, values);
    }

    @Override
    public int update(CarEntity type) {
        ContentValues values = new ContentValues();
        values.put(DBConstatants.DB_TABLE_I_CARS_A__ID, type.getId());
        values.put(DBConstatants.DB_TABLE_I_CARS_A__NAME, type.getName());
        return this.db.getDb().update(DBConstatants.DB_TABLE_I_CARS, values, DBConstatants.DB_TABLE_I_CARS_A__ID + "=" + type.getId(), null);
    }

    @Override
    public int delete(CarEntity type) {
        return this.db.getDb().delete(DBConstatants.DB_TABLE_I_CARS, DBConstatants.DB_TABLE_I_CARS_A__ID + "=" + type.getId(), null);
    }

    @Override
    public int deleteAll() {
        int rows = this.db.getDb().delete(DBConstatants.DB_TABLE_I_CARS, null, null);
        db.getDb().execSQL("vacuum");
        return rows;
    }

    @Override
    public CarEntity get(Long id) {
        Cursor c = null;
        CarEntity car = null;
        try {
            c = this.db.getDb().query(true, DBConstatants.DB_TABLE_I_CARS, DBConstatants.DB_TABLE_I_CARS_COLS, DBConstatants.DB_TABLE_I_CARS_A__ID + "=" + id, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                car = fillCar(c);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return car;
    }

    @Override
    public List<CarEntity> getAll() {
        ArrayList<CarEntity> ret = new ArrayList<CarEntity>();
        Cursor c = null;
        try {
            c = this.db.getDb().query(DBConstatants.DB_TABLE_I_CARS, DBConstatants.DB_TABLE_I_CARS_COLS, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                CarEntity car = fillCar(c);
                ret.add(car);
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
        Cursor mcursor = db.getDb().rawQuery(DBConstatants.DB_TABLE_CARS_COUNT, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }

    public void setSelected(long id) {
        ContentValues args = new ContentValues();
        args.put(DBConstatants.DB_TABLE_I_CARS_A__SELECTED, 0);
        this.db.getDb().update(DBConstatants.DB_TABLE_I_CARS, args, null, null);
        args = new ContentValues();
        args.put(DBConstatants.DB_TABLE_I_CARS_A__SELECTED, 1);
        this.db.getDb().update(DBConstatants.DB_TABLE_I_CARS, args, DBConstatants.DB_TABLE_I_CARS_A__ID + "=" + id, null);
    }

    private CarEntity fillCar(Cursor c) {
        CarEntity car = new CarEntity();
        int idx = c.getColumnIndex(DBConstatants.DB_TABLE_I_CARS_A__ID);
        car.setId(c.getLong(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_I_CARS_A__NAME);
        car.setName(c.getString(idx));
        idx = c.getColumnIndex(DBConstatants.DB_TABLE_I_CARS_A__SELECTED);
        car.setIsSelected(c.getInt(idx) == 1);
        return car;
    }
}
