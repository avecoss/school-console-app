package dev.alexcoss.dao;

import dev.alexcoss.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class JPAStudentDao implements StudentDao<Student> {

    private static final String SELECT_ALL_HQL = "SELECT s FROM Student s";
    private static final String SELECT_STUDENTS_IN_COURSE_HQL = """
        SELECT s FROM Course c
        JOIN c.students s
        WHERE c.name = :courseName
        """;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveItem(Student student) {
        try {
            entityManager.persist(student);
        } catch (DataAccessException e) {
            log.error("Error adding student to database. \nParameters: {}", student, e);
        }
    }

    @Override
    @Transactional
    public void updateItem(Student updateStudent) {
        try {
            entityManager.merge(updateStudent);
        } catch (DataAccessException e) {
            log.error("Error updating student to database. \nParameters: {}", updateStudent, e);
        }
    }

    @Override
    public Optional<Student> findItemById(int studentId) {
        try {
            Student student = entityManager.find(Student.class, studentId);
            return Optional.ofNullable(student);
        } catch (DataAccessException e) {
            log.error("Error getting student by id from database. \nParameters: {}", studentId, e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void deleteItemById(int studentId) {
        try {
            Student student = entityManager.find(Student.class, studentId);

            if (student != null) {
                entityManager.remove(student);
            } else {
                log.warn("Student with ID {} not found in the database. No deletion performed.", studentId);
            }
        } catch (DataAccessException e) {
            log.error("Error removing student from database. \nParameters: {}", studentId, e);
        }
    }

    @Override
    public List<Student> findAllItems() {
        try {
            return entityManager.createQuery(SELECT_ALL_HQL, Student.class).getResultList();
        } catch (DataAccessException e) {
            log.error("Error getting students from database. \nHQL: {}", SELECT_ALL_HQL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Student> findStudentsByCourse(String courseName) {
        try {

            return entityManager.createQuery(SELECT_STUDENTS_IN_COURSE_HQL, Student.class)
                .setParameter("courseName", courseName).getResultList();
        } catch (DataAccessException e) {
            log.error("Error getting students by course from database. \nHQL: {} \nCourse name: {}", SELECT_STUDENTS_IN_COURSE_HQL, courseName, e);
            return Collections.emptyList();
        }
    }
}

