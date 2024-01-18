package dev.alexcoss.dao;

import dev.alexcoss.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends AbstractDao<Student, List<Student>> {

    private static final String INSERT_SQL = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM students WHERE student_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM students";
    private static final String DELETE_SQL = "DELETE FROM students WHERE student_id = ?";
    private static final String SELECT_STUDENTS_IN_COURSE_SQL = "SELECT s.student_id, s.group_id, s.first_name, s.last_name\n" +
        "FROM courses as c\n" +
        "         JOIN students_courses as sc ON c.course_id = sc.course_id\n" +
        "         JOIN students as s ON sc.student_id = s.student_id\n" +
        "WHERE c.course_name = ?";

    public StudentDao(ConnectionFactory connectionFactory) {
        super(StudentDao.class.getName(), connectionFactory);
    }

    @Override
    public void addItem(Student student) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            setStudentValuesToStatement(student, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding student to database", INSERT_SQL, student);
        }
    }

    public void updateStudent(Student student) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            setStudentValuesToStatement(student, preparedStatement);
            preparedStatement.setInt(4, student.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error updating student to database", UPDATE_SQL, student);
        }
    }

    public Student getStudentById(int studentId) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            preparedStatement.setInt(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToObject(resultSet);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Error getting student by id from database", SELECT_BY_ID_SQL, studentId);
        }
        return null;
    }

    public void removeStudentById(int studentId) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error removing student from database", DELETE_SQL, studentId);
        }
    }

    @Override
    public List<Student> getAllItems() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Student student = resultSetToObject(resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting students from database", SELECT_ALL_SQL);
        }
        return students;
    }

    @Override
    public void addAllItems(List<Student> studentList) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Student student : studentList) {

                setStudentValuesToStatement(student, preparedStatement);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding students to database", INSERT_SQL, studentList);
        }
    }

    public List<Student> getStudentsByCourse(String courseName) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_IN_COURSE_SQL)) {
            preparedStatement.setString(1, courseName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(resultSetToObject(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting students by course from database", SELECT_STUDENTS_IN_COURSE_SQL);
        }

        return students;
    }

    @Override
    protected Student resultSetToObject(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getInt("student_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setGroupId(resultSet.getObject("group_id", Integer.class));

        return student;
    }

    private void setStudentValuesToStatement(Student student, PreparedStatement preparedStatement) throws SQLException {
        Integer groupId = null;
        if (!student.getDefaultInteger().equals(student.getGroupId())) {
            groupId = student.getGroupId();
        }

        preparedStatement.setObject(1, groupId, Types.INTEGER);
        preparedStatement.setString(2, student.getFirstName());
        preparedStatement.setString(3, student.getLastName());
    }
}

