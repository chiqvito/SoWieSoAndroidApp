package pl.chiqvito.sowieso.db.service;

import java.util.List;

import pl.chiqvito.sowieso.db.model.CategoryEntity;

public interface CategoriesService {

    void select(CategoryEntity category);

    List<CategoryEntity> categories();

}
