package pl.chiqvito.sowieso.db.dao;

import java.util.List;

public interface DAO<T, K> {

    K insert(T type);

    int update(T type);

    int delete(T type);

    int deleteAll();

    T get(K id);

    List<T> getAll();

    Integer count();

}
