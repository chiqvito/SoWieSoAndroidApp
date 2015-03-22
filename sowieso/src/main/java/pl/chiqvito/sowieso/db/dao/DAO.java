package pl.chiqvito.sowieso.db.dao;

import java.util.List;

public interface DAO<T, K> {

    Long insert(T type);

    void update(T type);

    void delete(T type);

    void deleteAll();

    T get(K id);

    List<T> getAll();

    Integer count();

}
