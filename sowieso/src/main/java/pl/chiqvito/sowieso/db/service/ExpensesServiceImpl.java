package pl.chiqvito.sowieso.db.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.ExpensesDao;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;

public class ExpensesServiceImpl implements ExpensesService {

    private static final String TAG = ExpensesServiceImpl.class.getName();

    private final ExpensesDao dao;

    public ExpensesServiceImpl(DbManager db) {
        dao = new ExpensesDao(db);
    }

    @Override
    public boolean save(ExpenseEntity expense) {
        try {
            if (expense.getId() == null) {
                Log.d(TAG, "insert " + expense);
                Long id = dao.insert(expense);
                Log.d(TAG, "insert id:" + id);
                if (id.intValue() > 0)
                    return true;
            } else {
                Log.d(TAG, "update " + expense);
                int rows = dao.update(expense);
                Log.d(TAG, "update rows:" + rows);
                if (rows == 1)
                    return true;
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<ExpenseEntity> getAllWithCategories() {
        return dao.getAllWithCategories();
    }

    @Override
    public List<ExpenseDTO> getAllExpenseDTOs() {
        List<ExpenseEntity> expenseEntities = dao.getAll();
        List<ExpenseDTO> data = new ArrayList<ExpenseDTO>();
        Log.v(TAG, "expenseEntities count: " + expenseEntities.size());
        for (ExpenseEntity exp : expenseEntities) {
            ExpenseDTO jExp = exp.toExpenseDTO();
            data.add(jExp);
        }
        Log.v(TAG, "json data count: " + data.size());
        return data;
    }

    @Override
    public void deleteAll() {
        int rows = dao.deleteAll();
        Log.v(TAG, "deleted:" + rows);
    }
}
