package com.aruma.mock.authone.account.service;

import java.util.List;

public interface CrudOperations<T> {
    T save(T body);

    T update(T body);

    T findById(Long id);

    List<T> findAll();

    void deleteById(Long id);
}
