package dev.alexcoss.dao;

import dev.alexcoss.mapper.GroupRowMapper;
import dev.alexcoss.model.Group;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GroupDao extends AbstractDao<Group, List<Group>> {

    private static final String INSERT_SQL = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM groups";
    private static final String SELECT_ALL_WITH_STUDENTS = "SELECT s.group_id, g.group_name, COUNT(s.student_id) as num_students\n" +
        "      FROM groups as g\n" +
        "      JOIN students as s ON g.group_id = s.group_id\n" +
        "      GROUP BY s.group_id, g.group_name\n" +
        "      ORDER BY num_students;";

    public GroupDao(JdbcTemplate jdbcTemplate) {
        super(GroupDao.class.getName(), jdbcTemplate);
    }

    @Override
    public void addItem(Group group) {
        try {
            jdbcTemplate.update(INSERT_SQL, group.getName());
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding group to database", INSERT_SQL, group);
        }
    }

    @Override
    public void addAllItems(List<Group> groupList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Group group = groupList.get(i);
                    ps.setString(1, group.getName());
                }

                @Override
                public int getBatchSize() {
                    return groupList.size();
                }
            });
        } catch (DataAccessException e) {
            handleSQLException(e, "Error adding groups to database", INSERT_SQL, groupList);
        }
    }

    @Override
    public List<Group> getAllItems() {
        try {
            return jdbcTemplate.query(SELECT_ALL_SQL, new GroupRowMapper());
        } catch (DataAccessException e) {
            handleSQLException(e, "Error getting groups from database", SELECT_ALL_SQL);
            return Collections.emptyList();
        }
    }

    public Map<Group, Integer> getAllGroupsWithStudents() {
        try {
            return jdbcTemplate.query(SELECT_ALL_WITH_STUDENTS, (resultSet, rowNum) -> {
                    Group group = new Group();
                    group.setId(resultSet.getInt("group_id"));
                    group.setName(resultSet.getString("group_name"));

                    int numStudents = resultSet.getInt("num_students");

                    return new AbstractMap.SimpleEntry<>(group, numStudents);
                }).stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        } catch (DataAccessException e) {
            handleSQLException(e, "Error getting all groups with students from database", SELECT_ALL_WITH_STUDENTS);
            return Collections.emptyMap();
        }
    }
}
