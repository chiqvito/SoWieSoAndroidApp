package pl.chiqvito.sowieso.db.service;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public interface ExpensesService {

    boolean save(ExpenseEntity expense);

}
