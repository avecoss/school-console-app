package dev.alexcoss.dao;

import dev.alexcoss.model.Group;
import dev.alexcoss.repository.GroupRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JPAGroupDao.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPAGroupDaoTest {
    @Autowired
    private Flyway flyway;
    @Autowired
    private JPAGroupDao groupDao;
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldAddItem() {
        Group group = Group.builder().name("Test").build();
        groupDao.saveItem(group);

        List<Group> retrievedCourses = groupDao.findAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(1, retrievedCourses.size());
        assertEquals(group, retrievedCourses.get(0));
    }

    @Test
    void shouldAddAllItems() {
        List<Group> groupList = new ArrayList<>();
        groupList.add(Group.builder().name("Test1").build());
        groupList.add(Group.builder().name("Test2").build());
        groupList.add(Group.builder().name("Test3").build());

        groupRepository.saveAllAndFlush(groupList);

        List<Group> retrievedGroups = groupDao.findAllItems();

        assertNotNull(retrievedGroups);
        assertEquals(groupList.size(), retrievedGroups.size());
        assertEquals(groupList, retrievedGroups);
    }

    @Test
    void shouldGetAllItems() {
        List<Group> groupList = new ArrayList<>();
        groupList.add(Group.builder().name("Test1").build());
        groupList.add(Group.builder().name("Test2").build());
        groupList.add(Group.builder().name("Test3").build());

        groupRepository.saveAllAndFlush(groupList);

        List<Group> retrievedGroups = groupDao.findAllItems();

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
        Map<Group, Integer> result = groupDao.findAllGroupsWithStudents();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(2, result.get(Group.builder().name("Test1").build()));
    }
}