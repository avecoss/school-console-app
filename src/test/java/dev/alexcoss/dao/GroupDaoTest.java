package dev.alexcoss.dao;

import dev.alexcoss.model.Group;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;

import java.sql.Connection;
import java.sql.PreparedStatement;;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GroupDaoTest {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE groups\n" +
        "(\n" +
        "    group_id   SERIAL PRIMARY KEY,\n" +
        "    group_name VARCHAR(100) NOT NULL\n" +
        ");";
    private static final String H2_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    private ConnectionFactory connectionFactory;
    private GroupDao groupDao;

    @BeforeEach
    void setUp() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);

        connectionFactory = new H2ConnectionFactory(dataSource);
        groupDao = new GroupDao(connectionFactory);

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE groups")) {
            preparedStatement.execute();
        }
    }

    @Test
    void shouldAddItem() {
        Group group = getTestGroup();
        groupDao.addItem(group);

        List<Group> retrievedCourses = groupDao.getAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(1, retrievedCourses.size());
        assertEquals(group, retrievedCourses.get(0));
    }

    @Test
    void shouldAddAllItems() {
        List<Group> groupList = new ArrayList<>();
        groupList.add(getTestGroup(1,"Test1"));
        groupList.add(getTestGroup(2,"Test2"));
        groupList.add(getTestGroup(3,"Test3"));

        groupDao.addAllItems(groupList);

        List<Group> retrievedGroups = groupDao.getAllItems();

        assertNotNull(retrievedGroups);
        assertEquals(groupList.size(), retrievedGroups.size());
        assertEquals(groupList, retrievedGroups);
    }

    @Test
    void shouldGetAllItems() {
        List<Group> groupList = new ArrayList<>();
        groupList.add(getTestGroup(1,"Test1"));
        groupList.add(getTestGroup(2,"Test2"));
        groupList.add(getTestGroup(3,"Test3"));

        groupDao.addAllItems(groupList);

        List<Group> retrievedGroups = groupDao.getAllItems();

        assertNotNull(retrievedGroups);
        assertEquals(groupList.size(), retrievedGroups.size());
        assertEquals(groupList, retrievedGroups);
    }

    private Group getTestGroup() {
        Group group = new Group();
        group.setId(1);
        group.setName("Test");

        return group;
    }

    private Group getTestGroup(int id, String name) {
        Group group = new Group();
        group.setId(id);
        group.setName(name);

        return group;
    }
}