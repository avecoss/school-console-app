package dev.alexcoss.dao;

import dev.alexcoss.model.Course;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JPACourseDao.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPACourseDaoTest {
    @Autowired
    private Flyway flyway;
    @Autowired
    private JPACourseDao courseDao;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldAddItem() {
        Course course = Course.builder()
            .name("Test")
            .build();

        courseDao.saveItem(course);

        List<Course> retrievedCourses = courseDao.findAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(1, retrievedCourses.size());
        assertEquals(course, retrievedCourses.get(0));
    }

    @Test
    void shouldAddAllItems() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().name("Test1").build());
        courseList.add(Course.builder().name("Test2").build());
        courseList.add(Course.builder().name("Test3").build());

        courseDao.saveAllItems(courseList);

        List<Course> retrievedCourses = courseDao.findAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(courseList.size(), retrievedCourses.size());
        assertEquals(courseList, retrievedCourses);
    }

    @Test
    void shouldGetCourseById() {
        Course course = Course.builder().name("Test").build();
        courseDao.saveItem(course);

        Optional<Course> retrievedCourse = courseDao.findItemById(1);

        assertTrue(retrievedCourse.isPresent());
        assertEquals(course, retrievedCourse.get());
    }

    @Test
    @Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/populate_courses.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void shouldGetAllItems() {
        List<Course> retrievedCourses = courseDao.findAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(3, retrievedCourses.size());
        assertEquals("name_2", retrievedCourses.get(1).getName());
    }
}