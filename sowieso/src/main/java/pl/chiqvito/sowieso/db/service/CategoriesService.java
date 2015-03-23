package pl.chiqvito.sowieso.db.service;

import java.util.List;

import pl.chiqvito.sowieso.db.model.CategoryEntity;
import pl.chiqvito.sowieso.rest.dto.CategoryDTO;

public interface CategoriesService {

    void select(CategoryEntity category);

    List<CategoryEntity> categories();

    void saveCategories(List<CategoryDTO> dtoList);

}
