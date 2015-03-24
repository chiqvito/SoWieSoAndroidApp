package pl.chiqvito.sowieso.db.service;

import java.util.List;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;

public interface ExpensesService {

    boolean save(ExpenseEntity expense);

    List<ExpenseEntity> getAllWithCategories();

    List<ExpenseDTO> getAllExpenseDTOs();

    void deleteAll();

}
