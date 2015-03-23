package pl.chiqvito.sowieso.db.service;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.CategoriesDao;

public class CategoriesServiceImpl implements CategoriesService {

    private static final String TAG = CategoriesServiceImpl.class.getName();

    private final CategoriesDao dao;

    public CategoriesServiceImpl(DbManager db) {
        dao = new CategoriesDao(db);
    }

}
