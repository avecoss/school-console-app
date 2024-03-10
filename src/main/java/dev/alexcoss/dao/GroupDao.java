package dev.alexcoss.dao;

import java.util.Map;

public interface GroupDao<T, E> extends Dao<T, E> {
    Map<T, Integer> findAllGroupsWithStudents();
}
