package dev.alexcoss.dao;

import dev.alexcoss.model.Course;
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
public class JPACourseDao implements CourseDao<Course> {
    private static final String SELECT_ALL_HQL = "SELECT c FROM Course c";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Course> findItemById(int courseId) {
        try {
            Course course = entityManager.find(Course.class, courseId);
            return Optional.ofNullable(course);
        } catch (DataAccessException e) {
            log.error("Error getting course by id from database. \nParameters: {}", courseId, e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void saveItem(Course course) {
        try {
            entityManager.persist(course);
            log.info("Adding course to database: {}", course);
        } catch (DataAccessException e) {
            log.error("Error adding course to database. \nParameters: {}", course, e);
        }
    }

    @Override
    public List<Course> findAllItems() {
        try {
            List<Course> courseList = entityManager.createQuery(SELECT_ALL_HQL, Course.class).getResultList();
            log.info("Getting courses from database: {}", courseList);
            return courseList;
        } catch (DataAccessException e) {
            log.error("Error getting courses from database. \nHQL: {}", SELECT_ALL_HQL, e);
            return Collections.emptyList();
        }
    }
}
