package dev.alexcoss.dao;

import java.util.Optional;

public interface CourseDao<T, E> extends Dao<T, E> {

    Optional<T> findItemById(int courseId);
}

