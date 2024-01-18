package dev.alexcoss.dao;

import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao extends AbstractDao<Course, List<Course>> {

    private static final String INSERT_SQL = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM courses";

    public CourseDao(ConnectionFactory connectionFactory) {
        super(CourseDao.class.getName(), connectionFactory);
    }

    @Override
    public void addItem(Course course) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding course to database", INSERT_SQL, course);
        }
    }

    @Override
    public void addAllItems(List<Course> courseList) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Course course : courseList) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setString(2, course.getDescription());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding courses to database", INSERT_SQL, courseList);
        }
    }

    @Override
    public List<Course> getAllItems() {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Course course = resultSetToObject(resultSet);
                courses.add(course);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting courses from database", SELECT_ALL_SQL);
        }

        return courses;
    }

    @Override
    protected Course resultSetToObject(ResultSet resultSet) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getInt("course_id"));
        course.setName(resultSet.getString("course_name"));
        course.setDescription(resultSet.getString("course_description"));

        return course;
    }
}
