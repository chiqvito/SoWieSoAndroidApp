package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ExpenseOperationEvent extends Event {

    private final Operation operation;
    private final ExpenseEntity expense;

    public ExpenseOperationEvent(Operation operation, ExpenseEntity expense) {
        this.operation = operation;
        this.expense = expense;
    }

    public ExpenseEntity getExpense() {
        return expense;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "ExpenseOperationEvent{expense=" + expense + ", operation=" + operation + '}';
    }

}
