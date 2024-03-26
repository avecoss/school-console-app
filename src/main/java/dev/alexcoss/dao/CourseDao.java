package dev.alexcoss.dao;

import java.util.Optional;

public interface CourseDao<T> extends Dao<T> {

    Optional<T> findItemById(int courseId);
}

