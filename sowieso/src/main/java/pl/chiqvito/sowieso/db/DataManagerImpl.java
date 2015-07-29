package pl.chiqvito.sowieso.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.util.Log;

import pl.chiqvito.sowieso.db.service.CarService;
import pl.chiqvito.sowieso.db.service.CarServiceImpl;
import pl.chiqvito.sowieso.db.service.CategoriesService;
import pl.chiqvito.sowieso.db.service.CategoriesServiceImpl;
import pl.chiqvito.sowieso.db.service.ExpensesService;
import pl.chiqvito.sowieso.db.service.ExpensesServiceImpl;
import pl.chiqvito.sowieso.db.service.PropertiesService;
import pl.chiqvito.sowieso.db.service.PropertiesServiceImpl;

public class DataManagerImpl implements DbServices, DbManager {

    private static final String TAG = DataManagerImpl.class.getName();

    private PropertiesService propertiesService;
    private CategoriesService categoriesService;
    private ExpensesService expensesService;
    private CarService carService;

    private final DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public DataManagerImpl(Context context) {
        Log.d(TAG, "data manager");
        dbOpenHelper = new DBOpenHelper(context);
        propertiesService = new PropertiesServiceImpl(this);
        categoriesService = new CategoriesServiceImpl(this);
        expensesService = new ExpensesServiceImpl(this);
        carService = new CarServiceImpl(this);
    }

    @Override
    public SQLiteDatabase getDb() {
        if (db != null && db.isOpen())
            return db;
        openDb();
        return db;
    }

    @Override
    public void openDb() {
        if (db == null || !db.isOpen())
            db = dbOpenHelper.getWritableDatabase();
        Log.i(TAG, "db open status: " + db.isOpen());
    }

    @Override
    public void closeDb() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void resetDb() {
        Log.i(TAG, "Resetting database connection (close and re-open).");
        closeDb();
        SystemClock.sleep(500);
        openDb();
    }

    @Override
    public PropertiesService propertiesService() {
        return propertiesService;
    }

    @Override
    public CategoriesService categoriesService() {
        return categoriesService;
    }

    @Override
    public ExpensesService expensesService() {
        return expensesService;
    }

    @Override
    public CarService carService() {
        return carService;
    }
}
