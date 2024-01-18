package dev.alexcoss.dao;

import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentsCoursesDaoTest {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE students_courses\n" +
        "(\n" +
        "    student_id INT REFERENCES students (student_id) ON DELETE CASCADE ,\n" +
        "    course_id  INT REFERENCES courses (course_id) ON DELETE CASCADE ,\n" +
        "    PRIMARY KEY (student_id, course_id)\n" +
        ");";
    private static final String CREATE_STUDENT_TABLE_SQL = "CREATE TABLE students\n" +
        "(\n" +
        "    student_id SERIAL PRIMARY KEY,\n" +
        "    group_id   INT,\n" +
        "    first_name VARCHAR(100) NOT NULL,\n" +
        "    last_name  VARCHAR(100) NOT NULL\n" +
        ");";
    private static final String CREATE_COURSE_TABLE_SQL = "CREATE TABLE courses\n" +
        "(\n" +
        "    course_id          SERIAL PRIMARY KEY,\n" +
        "    course_name        VARCHAR(100) NOT NULL,\n" +
        "    course_description VARCHAR(100)\n" +
        ");";
    private static final String H2_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    private ConnectionFactory connectionFactory;
    private StudentsCoursesDao studentsCoursesDao;

    @BeforeEach
    void setUp() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);

        connectionFactory = new H2ConnectionFactory(dataSource);
        initializeStudentAndCourseTables();
        fillOutTableStudent();
        fillOutTableCourse();

        studentsCoursesDao = new StudentsCoursesDao(connectionFactory);

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        dropTables("students_courses");
        dropTables("students");
        dropTables("courses");
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

    private void initializeStudentAndCourseTables() throws SQLException {
        initializeTable(CREATE_STUDENT_TABLE_SQL);
        initializeTable(CREATE_COURSE_TABLE_SQL);
    }

    private void initializeTable(String createTableSql) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute();
        }
    }

    private void dropTables(String tableName) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE " + tableName)) {
            preparedStatement.execute();
        }
    }

    private void fillOutTableStudent() {
        List<Student> students = new ArrayList<>();
        students.add(getTestStudent(1, "John"));
        students.add(getTestStudent(2, "Jane"));
        students.add(getTestStudent(3, "Jim"));

        StudentDao studentDao = new StudentDao(connectionFactory);
        studentDao.addAllItems(students);
    }

    private void fillOutTableCourse() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(getTestCourse(1, "Course 1"));
        courseList.add(getTestCourse(2, "Course 2"));
        courseList.add(getTestCourse(3, "Course 3"));

        CourseDao courseDao = new CourseDao(connectionFactory);
        courseDao.addAllItems(courseList);
    }

    private Student getTestStudent(int id, String name) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(name);
        student.setLastName("Doe");

        return student;
    }

    private Course getTestCourse(int id, String name) {
        Course course = new Course();
        course.setId(id);
        course.setName(name);

        return course;
    }
}
