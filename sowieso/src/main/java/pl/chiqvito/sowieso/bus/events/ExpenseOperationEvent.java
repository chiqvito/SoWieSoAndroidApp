package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ExpenseOperationEvent {

    public final static int SAVE = 0;
    public final static int SAVE_ALL_ON_SERVER = 1;
    public final static int GET_ALL_WITH_CATEGORY = 2;

    private final int operation;
    private final ExpenseEntity expense;

    public ExpenseOperationEvent(int operation, ExpenseEntity expense) {
        this.operation = operation;
        this.expense = expense;
    }

    public ExpenseEntity getExpense() {
        return expense;
    }

    public int getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "ExpenseOperationEvent{expense=" + expense + ", operation=" + operation + '}';
    }

}
