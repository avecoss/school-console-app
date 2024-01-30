package dev.alexcoss.dao;

import dev.alexcoss.mapper.StudentRowMapper;
import dev.alexcoss.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

@Repository
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

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate) {
        super(StudentDao.class.getName(), jdbcTemplate);
    }

    @Override
    public void addItem(Student student) {
        try {
            jdbcTemplate.update(INSERT_SQL, getStudentParameters(student));
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding student to database", INSERT_SQL, student);
        }
    }

    public void updateStudent(Student updateStudent) {
        try {
            jdbcTemplate.update(UPDATE_SQL, getStudentParameters(updateStudent, updateStudent.getId()));
        } catch (DataAccessException e) {
            handleSQLException(e, "Error updating student to database", UPDATE_SQL, updateStudent);
        }
    }

    public Student getStudentById(int studentId) {
        try {
            return jdbcTemplate.query(SELECT_BY_ID_SQL, new StudentRowMapper(), studentId)
                .stream()
                .findAny()
                .orElseThrow(() -> new EmptyResultDataAccessException(studentId));
        } catch (EmptyResultDataAccessException e) {
            handleSQLException(e, "Error getting student by id from database", SELECT_BY_ID_SQL, studentId);
            return null;
        }
    }

    public void removeStudentById(int studentId) {
        try {
            jdbcTemplate.update(DELETE_SQL, studentId);
        } catch (DataAccessException e) {
            handleSQLException(e, "Error removing student from database", DELETE_SQL, studentId);
        }
    }

    @Override
    public List<Student> getAllItems() {
        try {
            return jdbcTemplate.query(SELECT_ALL_SQL, new StudentRowMapper());
        } catch (DataAccessException e) {
            handleSQLException(e, "Error getting students from database", SELECT_ALL_SQL);
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
                    Integer groupId = (!student.getDefaultInteger().equals(student.getGroupId())) ? student.getGroupId() : null;

                    ps.setObject(1, groupId, Types.INTEGER);
                    ps.setString(2, student.getFirstName());
                    ps.setString(3, student.getLastName());
                }

                @Override
                public int getBatchSize() {
                    return studentList.size();
                }
            });
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding students to database", INSERT_SQL, studentList);
        }
    }

    public List<Student> getStudentsByCourse(String courseName) {
        try {
            return jdbcTemplate.query(SELECT_STUDENTS_IN_COURSE_SQL, new StudentRowMapper(), courseName);
        } catch (DataAccessException e) {
            handleSQLException(e, "Error getting students by course from database", SELECT_STUDENTS_IN_COURSE_SQL);
            return Collections.emptyList();
        }
    }

    private Object[] getStudentParameters(Student student) {
        Integer groupId = getGroupId(student);
        return new Object[]{groupId, student.getFirstName(), student.getLastName()};
    }

    private Object[] getStudentParameters(Student student, Integer id) {
        Integer groupId = getGroupId(student);
        return new Object[]{groupId, student.getFirstName(), student.getLastName(), id};
    }

    private Integer getGroupId(Student student) {
        Integer groupId = null;
        if (!student.getDefaultInteger().equals(student.getGroupId())) {
            groupId = student.getGroupId();
        }
        return groupId;
    }
}

