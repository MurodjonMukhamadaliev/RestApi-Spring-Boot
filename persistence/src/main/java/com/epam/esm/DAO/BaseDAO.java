package com.epam.esm.DAO;

import java.util.UUID;



public interface BaseDAO<T> {

    T create(T t);

    T update(T t);

    void delete(T t);

    T get(UUID id);

}
