package pl.chiqvito.sowieso.db.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.CategoriesDao;
import pl.chiqvito.sowieso.db.model.CategoryEntity;
import pl.chiqvito.sowieso.rest.dto.CategoryDTO;

public class CategoriesServiceImpl implements CategoriesService {

    private static final String TAG = CategoriesServiceImpl.class.getName();

    private final CategoriesDao dao;

    public CategoriesServiceImpl(DbManager db) {
        dao = new CategoriesDao(db);
    }

    @Override
    public void select(CategoryEntity category) {
        dao.setSelected(category.getId());
    }

    @Override
    public List<CategoryEntity> categories() {
        List<CategoryEntity> cats = new ArrayList<CategoryEntity>();
        List<CategoryEntity> mainCats = dao.getMainCategories();
        for (CategoryEntity cat : mainCats) {
            cats.add(cat);
            List<CategoryEntity> subCates = dao.getSubCategories(cat.getId());
            cats.addAll(subCates);
        }
        return cats;
    }

    @Override
    public void saveCategories(List<CategoryDTO> dtoList) {
        Log.d(TAG, "delete all categories");
        dao.deleteAll();
        for (CategoryDTO c : dtoList) {
            Log.d(TAG, "saving to db: " + c);
            CategoryEntity ce = c.toCategoryEntity();
            Log.d(TAG, "inserting: " + ce);
            dao.insert(ce);
        }
    }
}
