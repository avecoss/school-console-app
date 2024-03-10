package dev.alexcoss.dao;

public interface Dao<T, E> {

    void saveItem(T item);

    E findAllItems();

    void saveAllItems(E items);
}
