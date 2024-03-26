package dev.alexcoss.dao;

import java.util.List;
import java.util.Optional;

public interface StudentDao<T> extends Dao<T> {

    void updateItem(T student);

    Optional<T> findItemById(int studentId);

    void deleteItemById(int studentId);

    List<T> findStudentsByCourse(String courseName);
}
