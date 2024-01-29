package dev.alexcoss.mapper;

import dev.alexcoss.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getInt("course_id"));
        course.setName(resultSet.getString("course_name"));
        course.setDescription(resultSet.getString("course_description"));

        return course;
    }
}
