package dev.alexcoss.dao;

import dev.alexcoss.mapper.StudentRowMapper;
import dev.alexcoss.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class StudentDao implements Dao<Student, List<Student>> {

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

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addItem(Student student) {
        try {
            jdbcTemplate.update(INSERT_SQL, student.getGroupId(), student.getFirstName(), student.getLastName());
            log.info("Adding student to database: {}", student);
        } catch (DataAccessException e) {
            log.error("Error adding student to database. \nSQL: {} \nParameters: {}", INSERT_SQL, student, e);
        }
    }

    public void updateStudent(Student updateStudent) {
        try {
            jdbcTemplate.update(UPDATE_SQL, updateStudent.getGroupId(), updateStudent.getFirstName(), updateStudent.getLastName(), updateStudent.getId());
            log.info("Updating student to database: {}", updateStudent);
        } catch (DataAccessException e) {
            log.error("Error updating student to database. \nSQL: {} \nParameters: {}", UPDATE_SQL, updateStudent, e);
        }
    }

    public Optional<Student> getStudentById(int studentId) {
        try {
            return jdbcTemplate.query(SELECT_BY_ID_SQL, new StudentRowMapper(), studentId)
                .stream()
                .findAny();
        } catch (DataAccessException e) {
            log.error("Error getting student by id from database. \nSQL: {} \nParameters: {}", SELECT_BY_ID_SQL, studentId, e);
            return Optional.empty();
        }
    }

    public void removeStudentById(int studentId) {
        try {
            jdbcTemplate.update(DELETE_SQL, studentId);
            log.info("Removing student to database: {}", studentId);
        } catch (DataAccessException e) {
            log.error("Error removing student from database. \nSQL: {} \nParameters: {}", DELETE_SQL, studentId, e);
        }
    }

    @Override
    public List<Student> getAllItems() {
        try {
            List<Student> students = jdbcTemplate.query(SELECT_ALL_SQL, new StudentRowMapper());
            log.info("Getting students from database: {}", students);
            return students;
        } catch (DataAccessException e) {
            log.error("Error getting students from database. \nSQL: {}", SELECT_ALL_SQL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public void addAllItems(List<Student> studentList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Student student = studentList.get(i);

                    ps.setObject(1, student.getGroupId(), Types.INTEGER);
                    ps.setString(2, student.getFirstName());
                    ps.setString(3, student.getLastName());
                }

                @Override
                public int getBatchSize() {
                    return studentList.size();
                }
            });
            log.info("Adding students to database: {}", studentList);
        } catch (DataAccessException e) {
            log.error("Error adding students to database. \nSQL: {} \nParameters: {}", INSERT_SQL, studentList, e);
        }
    }

    public List<Student> getStudentsByCourse(String courseName) {
        try {
            List<Student> students = jdbcTemplate.query(SELECT_STUDENTS_IN_COURSE_SQL, new StudentRowMapper(), courseName);
            log.info("Getting students by course from database: {} \nCourse name: {}", students, courseName);
            return students;
        } catch (DataAccessException e) {
            log.error("Error getting students by course from database. \nSQL: {} \nCourse name: {}", INSERT_SQL, courseName, e);
            return Collections.emptyList();
        }
    }
}

