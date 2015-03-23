package pl.chiqvito.sowieso.db.service;

import java.util.List;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public interface ExpensesService {

    boolean save(ExpenseEntity expense);

    List<ExpenseEntity> getAllWithCategories();

}
