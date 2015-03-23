package pl.chiqvito.sowieso.db.service;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.dao.CategoriesDao;
import pl.chiqvito.sowieso.db.model.CategoryEntity;

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
}
