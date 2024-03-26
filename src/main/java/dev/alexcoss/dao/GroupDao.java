package dev.alexcoss.dao;

import java.util.Map;

public interface GroupDao<T> extends Dao<T> {
    Map<T, Integer> findAllGroupsWithStudents();
}
