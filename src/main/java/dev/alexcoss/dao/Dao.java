package dev.alexcoss.dao;

public interface Dao<T, E> {

    void addItem(T item);

    E getAllItems();

    void addAllItems(E items);
}
