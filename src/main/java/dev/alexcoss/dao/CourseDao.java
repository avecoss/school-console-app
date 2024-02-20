package dev.alexcoss.dao;

import dev.alexcoss.mapper.CourseRowMapper;
import dev.alexcoss.model.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CourseDao implements Dao<Course, List<Course>> {

    private static final String INSERT_SQL = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM courses";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addItem(Course course) {
        try {
            jdbcTemplate.update(INSERT_SQL, course.getName(), course.getDescription());
            log.info("Adding course to database: {}", course);
        } catch (DataAccessException e) {
            log.error("Error adding course to database. \nSQL: {} \nParameters: {}", INSERT_SQL, course, e);
        }
    }

    @Override
    public void addAllItems(List<Course> courseList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Course course = courseList.get(i);

                    ps.setString(1, course.getName());
                    ps.setString(2, course.getDescription());
                }

                @Override
                public int getBatchSize() {
                    return courseList.size();
                }
            });
            log.info("Adding courses to database: {}", courseList);
        } catch (DataAccessException e) {
            log.error("Error adding courses to database. \nSQL: {} \nParameters: {}", INSERT_SQL, courseList, e);
        }
    }

    @Override
    public List<Course> getAllItems() {
        try {
            List<Course> courses = jdbcTemplate.query(SELECT_ALL_SQL, new CourseRowMapper());
            log.info("Getting courses from database: {}", courses);
            return courses;
        } catch (DataAccessException e) {
            log.error("Error getting courses from database. \nSQL: {}", SELECT_ALL_SQL, e);
            return Collections.emptyList();
        }
    }
}
