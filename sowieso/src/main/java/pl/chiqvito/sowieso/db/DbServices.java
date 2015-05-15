package pl.chiqvito.sowieso.db;

import pl.chiqvito.sowieso.db.service.CarService;
import pl.chiqvito.sowieso.db.service.CategoriesService;
import pl.chiqvito.sowieso.db.service.ExpensesService;
import pl.chiqvito.sowieso.db.service.PropertiesService;

public interface DbServices {

    PropertiesService propertiesService();

    CategoriesService categoriesService();

    ExpensesService expensesService();

    CarService carService();

}
