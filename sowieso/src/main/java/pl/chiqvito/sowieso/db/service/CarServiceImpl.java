package pl.chiqvito.sowieso.db.service;

import android.util.Log;

import java.util.List;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.CarsDao;
import pl.chiqvito.sowieso.db.model.CarEntity;
import pl.chiqvito.sowieso.rest.dto.InventoryCarDTO;

public class CarServiceImpl implements CarService {
    private static final String TAG = CarServiceImpl.class.getName();

    private final CarsDao dao;

    public CarServiceImpl(DbManager db) {
        dao = new CarsDao(db);
    }

    @Override
    public void select(CarEntity car) {
        dao.setSelected(car.getId());
    }

    @Override
    public List<CarEntity> cars() {
        return dao.getAll();
    }

    @Override
    public void saveCars(List<InventoryCarDTO> dtoList) {
        Log.d(TAG, "delete all cars");
        dao.deleteAll();
        for (InventoryCarDTO c : dtoList) {
            Log.d(TAG, "saving to db: " + c);
            CarEntity ce = c.toCarEntity();
            Log.d(TAG, "inserting: " + ce);
            dao.insert(ce);
        }
    }
}
