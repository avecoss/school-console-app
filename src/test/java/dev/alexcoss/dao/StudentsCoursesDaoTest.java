package dev.alexcoss.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@JdbcTest
@Sql(
    scripts = {
        "/sql/clear_tables.sql",
        "/sql/populate_groups.sql",
        "/sql/populate_students.sql",
        "/sql/populate_courses.sql",
        "/sql/populate_students_courses.sql"
    },
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentsCoursesDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentsCoursesDao studentsCoursesDao;

    @BeforeEach
    void setUp() {
        studentsCoursesDao = new StudentsCoursesDao(jdbcTemplate);
    }

    @Test
    void shouldAddAllItems() {
        Map<Integer, Set<Integer>> dataToAdd = new HashMap<>();
        dataToAdd.put(1, new HashSet<>(Arrays.asList(1, 2, 3)));
        dataToAdd.put(2, new HashSet<>(Arrays.asList(1, 2)));
        dataToAdd.put(3, new HashSet<>(Arrays.asList(2, 3)));

        studentsCoursesDao.addAllItems(dataToAdd);

        Map<Integer, Set<Integer>> retrievedData = studentsCoursesDao.getAllItems();

        assertEquals(3, retrievedData.size());
        assertEquals(dataToAdd.keySet(), retrievedData.keySet());
        for (Integer key : dataToAdd.keySet()) {
            Set<Integer> expectedSet = dataToAdd.get(key);
            Set<Integer> retrievedSet = retrievedData.get(key);
            assertEquals(expectedSet, retrievedSet);
        }
    }

    @Test
    void shouldAddItem() {
        Map<Integer, Integer> dataToAdd = new HashMap<>();
        dataToAdd.put(1, 2);

        studentsCoursesDao.addItem(dataToAdd);

        Map<Integer, Set<Integer>> retrievedData = studentsCoursesDao.getAllItems();

        assertEquals(dataToAdd.size(), retrievedData.size());
        assertEquals(dataToAdd.keySet(), retrievedData.keySet());

        Integer expected = dataToAdd.get(1);
        Set<Integer> retrievedSet = retrievedData.get(1);

        assertEquals(Collections.singleton(expected), retrievedSet);
    }

    @Test
    void shouldRemoveItems() {
        Map<Integer, Set<Integer>> dataToAdd = new HashMap<>();
        dataToAdd.put(1, new HashSet<>(Arrays.asList(1, 2, 3)));
        dataToAdd.put(2, new HashSet<>(Arrays.asList(1, 2)));
        dataToAdd.put(3, new HashSet<>(Arrays.asList(2, 3)));
        studentsCoursesDao.addAllItems(dataToAdd);

        Map<Integer, Integer> dataToRemove = new HashMap<>();
        dataToRemove.put(1, 2);
        dataToRemove.put(2, 1);

        studentsCoursesDao.removeItems(dataToRemove);

        Map<Integer, Set<Integer>> retrievedData = studentsCoursesDao.getAllItems();

        for (Map.Entry<Integer, Integer> entry : dataToRemove.entrySet()) {
            Integer studentId = entry.getKey();
            Integer courseId = entry.getValue();

            assertTrue(retrievedData.containsKey(studentId));
            assertFalse(retrievedData.get(studentId).contains(courseId));
        }
    }

    @Test
    void shouldGetAllItems() {
        Map<Integer, Set<Integer>> dataToAdd = new HashMap<>();
        dataToAdd.put(1, new HashSet<>(Arrays.asList(1, 2, 3)));
        dataToAdd.put(2, new HashSet<>(Arrays.asList(1, 2)));
        dataToAdd.put(3, new HashSet<>(Arrays.asList(2, 3)));

        studentsCoursesDao.addAllItems(dataToAdd);

        Map<Integer, Set<Integer>> retrievedData = studentsCoursesDao.getAllItems();

        assertEquals(3, retrievedData.size());
        assertEquals(dataToAdd.keySet(), retrievedData.keySet());
        for (Integer key : dataToAdd.keySet()) {
            Set<Integer> expectedSet = dataToAdd.get(key);
            Set<Integer> retrievedSet = retrievedData.get(key);
            assertEquals(expectedSet, retrievedSet);
        }
    }
}
