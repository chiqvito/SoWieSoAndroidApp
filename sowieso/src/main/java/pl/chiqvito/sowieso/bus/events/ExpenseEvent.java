package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ExpenseEvent {

    private final ExpenseEntity expense;

    public ExpenseEvent(ExpenseEntity expense) {
        this.expense = expense;
    }

    public ExpenseEntity getExpense() {
        return expense;
    }

    @Override
    public String toString() {
        return "ExpenseEvent{expense=" + expense + '}';
    }

}
