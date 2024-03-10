package dev.alexcoss.dao;

import java.util.List;
import java.util.Optional;

public interface StudentDao <T, E> extends Dao<T, E>{

    void updateItem(T student);
    Optional<T> findItemById(int studentId);
    void deleteItemById(int studentId);
    List<T> findStudentsByCourse(String courseName);
}
