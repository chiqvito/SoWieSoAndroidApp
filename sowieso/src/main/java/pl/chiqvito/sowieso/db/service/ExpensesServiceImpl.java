package pl.chiqvito.sowieso.db.service;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.ExpensesDao;

public class ExpensesServiceImpl implements ExpensesService {

    private static final String TAG = ExpensesServiceImpl.class.getName();

    private final ExpensesDao dao;

    public ExpensesServiceImpl(DbManager db) {
        dao = new ExpensesDao(db);
    }
}
