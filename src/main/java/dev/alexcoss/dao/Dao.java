package dev.alexcoss.dao;

import java.util.List;

public interface Dao<T> {

    void saveItem(T item);

    List<T> findAllItems();
}
