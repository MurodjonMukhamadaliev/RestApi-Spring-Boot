package com.epam.esm.service;

import java.util.UUID;


public interface BaseService<T, DT> {

    T create(DT dto);

    T update(UUID id, DT dto);

    void delete(UUID id);

    T get(UUID id);

}
