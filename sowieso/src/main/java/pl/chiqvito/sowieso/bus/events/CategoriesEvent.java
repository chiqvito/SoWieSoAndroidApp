package pl.chiqvito.sowieso.bus.events;

import java.util.List;

import pl.chiqvito.sowieso.db.model.CategoryEntity;

public class CategoriesEvent {

    private final List<CategoryEntity> categories;

    public CategoriesEvent(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "CategoriesEvent{categories=" + categories + '}';
    }
}
