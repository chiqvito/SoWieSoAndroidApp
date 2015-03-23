package pl.chiqvito.sowieso.bus.events;

import java.util.List;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ExpensesEvent {

    List<ExpenseEntity> expenses;

    public ExpensesEvent(List<ExpenseEntity> expenses) {
        this.expenses = expenses;
    }

    public List<ExpenseEntity> getExpenses() {
        return expenses;
    }

    @Override
    public String toString() {
        return "ExpensesEvent{expenses=" + expenses + '}';
    }
    
}
