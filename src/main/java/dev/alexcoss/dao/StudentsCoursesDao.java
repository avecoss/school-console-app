package dev.alexcoss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentsCoursesDao extends AbstractDao<Map<Integer, Integer>, Map<Integer, Set<Integer>>> {

    private static final String INSERT_SQL = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM students_courses";
    private static final String NUMBER_OF_EXISTING_SQL = "SELECT COUNT(*) FROM students_courses WHERE student_id = ? AND course_id = ?";

    @Autowired
    public StudentsCoursesDao(JdbcTemplate jdbcTemplate) {
        super(StudentsCoursesDao.class.getName(), jdbcTemplate);
    }

    @Override
    public void addAllItems(Map<Integer, Set<Integer>> map) {
        List<Object[]> batchArgs = new ArrayList<>();

        map.forEach((studentId, values) -> {
            for (Integer courseId : values) {
                if (!exists(studentId, courseId)) {
                    batchArgs.add(new Object[]{studentId, courseId});
                } else {
                    logger.warning("Skipping duplicate entry: " + studentId + ", " + courseId);
                }
            }
        });

        try {
            jdbcTemplate.batchUpdate(INSERT_SQL, batchArgs);
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding related table to database", INSERT_SQL, map);
        }
    }

    @Override
    public void addItem(Map<Integer, Integer> map) {
        batchUpdate(map, INSERT_SQL, "Error adding related table to database");
    }

    public void removeItems(Map<Integer, Integer> map) {
        batchUpdate(map, DELETE_SQL, "Error removing related table from database");
    }

    @Override
    public Map<Integer, Set<Integer>> getAllItems() {
        try {
            return jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, rowNum) -> {
                    int studentId = resultSet.getInt("student_id");
                    int courseId = resultSet.getInt("course_id");

                    return Map.entry(studentId, courseId);
                }).stream()
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

        } catch (DataAccessException e) {
            handleSQLException(e, "Error getting items from database", SELECT_ALL_SQL);
            return Collections.emptyMap();
        }
    }

    private void setValuesToStatement(PreparedStatement preparedStatement, int... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setInt(i + 1, values[i]);
        }
    }

    private void batchUpdate(Map<Integer, Integer> map, String sql, String errorMessage) {
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Map.Entry<Integer, Integer> entry = map.entrySet().toArray(new Map.Entry[0])[i];
                    setValuesToStatement(ps, entry.getKey(), entry.getValue());
                }

                @Override
                public int getBatchSize() {
                    return map.size();
                }
            });
        } catch (DataAccessException e) {
            handleSQLException(e, errorMessage, sql, map);
        }
    }

    private boolean exists(int studentId, int courseId) {
        Integer result = jdbcTemplate.queryForObject(NUMBER_OF_EXISTING_SQL, Integer.class, studentId, courseId);
        return result != null && result > 0;
    }
}
