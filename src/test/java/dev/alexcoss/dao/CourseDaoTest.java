package dev.alexcoss.dao;

import dev.alexcoss.model.Course;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ActiveProfiles("test")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Flyway flyway;
    private CourseDao courseDao;

    @BeforeEach
    void setUp() {
        flyway.clean();
        courseDao = new CourseDao(jdbcTemplate);
        flyway.migrate();
    }

    @Test
    void shouldAddItem() {
        Course course = getTestCourse();
        courseDao.addItem(course);

        List<Course> retrievedCourses = courseDao.getAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(1, retrievedCourses.size());
        assertEquals(course, retrievedCourses.get(0));
    }

    @Test
    void shouldAddAllItems() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(getTestCourse(1, "Test1"));
        courseList.add(getTestCourse(2, "Test2"));
        courseList.add(getTestCourse(3, "Test3"));

        courseDao.addAllItems(courseList);

        List<Course> retrievedCourses = courseDao.getAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(courseList.size(), retrievedCourses.size());
        assertEquals(courseList, retrievedCourses);
    }

    @Test
    @Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/populate_courses.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void shouldGetAllItems() {
        List<Course> retrievedCourses = courseDao.getAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(3, retrievedCourses.size());
        assertEquals("name_2", retrievedCourses.get(1).getName());
    }

    private Course getTestCourse() {
        Course course = new Course();
        course.setId(1);
        course.setName("Test");

        return course;
    }

    private Course getTestCourse(int id, String name) {
        Course course = new Course();
        course.setId(id);
        course.setName(name);

        return course;
    }
}