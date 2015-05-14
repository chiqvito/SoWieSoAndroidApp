package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.db.model.CategoryEntity;

public class CategoryOperationEvent extends Event {

    private final Operation operation;
    private final CategoryEntity category;

    public CategoryOperationEvent(Operation operation, CategoryEntity category) {
        this.operation = operation;
        this.category = category;
    }

    public Operation getOperation() {
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
