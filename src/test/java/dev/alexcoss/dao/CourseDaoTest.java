package dev.alexcoss.dao;

import dev.alexcoss.model.Course;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoTest {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE courses\n" +
        "(\n" +
        "    course_id          SERIAL PRIMARY KEY,\n" +
        "    course_name        VARCHAR(100) NOT NULL,\n" +
        "    course_description VARCHAR(100)\n" +
        ");";
    private static final String H2_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    private ConnectionFactory connectionFactory;
    private CourseDao courseDao;

    @BeforeEach
    void setUp() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);

        connectionFactory = new H2ConnectionFactory(dataSource);
        courseDao = new CourseDao(connectionFactory);

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE courses")) {
            preparedStatement.execute();
        }
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
        courseList.add(getTestCourse(1,"Test1"));
        courseList.add(getTestCourse(2,"Test2"));
        courseList.add(getTestCourse(3,"Test3"));

        courseDao.addAllItems(courseList);

        List<Course> retrievedCourses = courseDao.getAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(courseList.size(), retrievedCourses.size());
        assertEquals(courseList, retrievedCourses);
    }

    @Test
    void shouldGetAllItems() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(getTestCourse(1,"Test1"));
        courseList.add(getTestCourse(2,"Test2"));
        courseList.add(getTestCourse(3,"Test3"));

        courseDao.addAllItems(courseList);

        List<Course> retrievedCourses = courseDao.getAllItems();

        assertNotNull(retrievedCourses);
        assertEquals(courseList.size(), retrievedCourses.size());
        assertEquals(courseList, retrievedCourses);
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