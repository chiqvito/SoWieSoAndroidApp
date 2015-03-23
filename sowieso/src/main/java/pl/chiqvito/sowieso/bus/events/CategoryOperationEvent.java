package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.db.model.CategoryEntity;

public class CategoryOperationEvent {

    public final static int SELECT = 0;
    public final static int GET_ALL = 1;

    private final int operation;
    private final CategoryEntity category;

    public CategoryOperationEvent(int operation, CategoryEntity category) {
        this.operation = operation;
        this.category = category;
    }

    public int getOperation() {
        return operation;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "CategoryOperationEvent{operation=" + operation + ", category=" + category + '}';
    }
}
