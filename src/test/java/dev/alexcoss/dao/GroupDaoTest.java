package dev.alexcoss.dao;

import dev.alexcoss.model.Group;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ActiveProfiles("test")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Flyway flyway;
    private GroupDao groupDao;

    @BeforeEach
    void setUp() {
        flyway.clean();
        groupDao = new GroupDao(jdbcTemplate);
        flyway.migrate();
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

    @Test
    @Sql(
        scripts = {
            "/sql/clear_tables.sql",
            "/sql/populate_groups.sql",
            "/sql/populate_students.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void shouldGetAllGroupsWithStudents() {
        Map<Group, Integer> result = groupDao.getAllGroupsWithStudents();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(2, result.get(getTestGroup(1, "Test1")));
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