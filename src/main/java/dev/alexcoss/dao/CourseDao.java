package dev.alexcoss.dao;

import dev.alexcoss.mapper.CourseRowMapper;
import dev.alexcoss.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class CourseDao extends AbstractDao<Course, List<Course>> {

    private static final String INSERT_SQL = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM courses";

    public CourseDao(JdbcTemplate jdbcTemplate) {
        super(CourseDao.class.getName(), jdbcTemplate);
    }

    @Override
    public void addItem(Course course) {
        try {
            jdbcTemplate.update(INSERT_SQL, course.getName(), course.getDescription());
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding course to database", INSERT_SQL, course);
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
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding courses to database", INSERT_SQL, courseList);
        }
    }

    @Override
    public List<Course> getAllItems() {
        try {
            return jdbcTemplate.query(SELECT_ALL_SQL, new CourseRowMapper());
        } catch (DataAccessException e) {
            handleSQLException(e, "Error getting courses from database", SELECT_ALL_SQL);
            return Collections.emptyList();
        }
    }
}
