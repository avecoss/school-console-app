package dev.alexcoss.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StudentsCoursesDao extends AbstractDao<Map<Integer, Integer>, Map<Integer, Set<Integer>>> {

    private static final String INSERT_SQL = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM students_courses";
    private static final String NUMBER_OF_EXISTING_SQL = "SELECT COUNT(*) FROM students_courses WHERE student_id = ? AND course_id = ?";

    public StudentsCoursesDao(ConnectionFactory connectionFactory) {
        super(StudentsCoursesDao.class.getName(), connectionFactory);
    }

    @Override
    public void addAllItems(Map<Integer, Set<Integer>> map) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
                int studentId = entry.getKey();
                Set<Integer> coursesIdList = entry.getValue();

                if (!exists(studentId, coursesIdList, connection)) {
                    setValuesToStatement(preparedStatement, coursesIdList, studentId);
                } else {
                    logger.warning("Skipping duplicate entry for student_id: " + studentId);
                }
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding related table to database", INSERT_SQL, map);
        }
    }

    @Override
    public void addItem(Map<Integer, Integer> map) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int studentId = entry.getKey();
                int courseId = entry.getValue();
                if (!exists(studentId, courseId, connection)) {
                    setValuesToStatement(studentId, courseId, preparedStatement);
                } else {
                    logger.warning("Skipping duplicate entry for student_id: " + studentId);
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding related table to database", INSERT_SQL, map);
        }
    }

    public void removeItems(Map<Integer, Integer> map) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                setValuesToStatement(entry.getKey(), entry.getValue(), preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error removing related table from database", DELETE_SQL, map);
        }
    }

    @Override
    public Map<Integer, Set<Integer>> getAllItems() {
        Map<Integer, Set<Integer>> result = new HashMap<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int courseId = resultSet.getInt("course_id");

                result.computeIfAbsent(studentId, k -> new HashSet<>()).add(courseId);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting items from database", SELECT_ALL_SQL);
        }

        return result;
    }

    @Override
    protected Map<Integer, Integer> resultSetToObject(ResultSet resultSet) throws SQLException {
        Map<Integer, Integer> result = new HashMap<>();

        int studentId = resultSet.getInt("student_id");
        int courseId = resultSet.getInt("course_id");

        result.put(studentId, courseId);

        return result;
    }

    private void setValuesToStatement(PreparedStatement preparedStatement, Set<Integer> coursesIdList, int studentId) throws SQLException {
        for (Integer courseId : coursesIdList) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.addBatch();
        }
    }

    private void setValuesToStatement(Integer studentId, Integer courseId, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, studentId);
        preparedStatement.setInt(2, courseId);
    }

    private boolean exists(int studentId, Set<Integer> coursesIdList, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(NUMBER_OF_EXISTING_SQL)) {

            for (Integer courseId : coursesIdList) {
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, courseId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean exists(int studentId, int courseId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(NUMBER_OF_EXISTING_SQL)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }
}
