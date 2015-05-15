package pl.chiqvito.sowieso.db.service;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.CarsDao;

public class CarServiceImpl implements CarService {
    private static final String TAG = CarServiceImpl.class.getName();

    private final CarsDao dao;

    public CarServiceImpl(DbManager db) {
        dao = new CarsDao(db);
    }
}
