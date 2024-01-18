package dev.alexcoss.dao;

import dev.alexcoss.model.Student;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE students\n" +
        "(\n" +
        "    student_id SERIAL PRIMARY KEY,\n" +
        "    group_id   INT,\n" +
        "    first_name VARCHAR(100) NOT NULL,\n" +
        "    last_name  VARCHAR(100) NOT NULL\n" +
        ");";
    private static final String H2_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    private ConnectionFactory connectionFactory;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);

        connectionFactory = new H2ConnectionFactory(dataSource);
        studentDao = new StudentDao(connectionFactory);

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE students")) {
            preparedStatement.execute();
        }
    }

    @Test
    @Order(1)
    void shouldAddItem() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        Student retrievedStudent = studentDao.getStudentById(1);

        assertNotNull(retrievedStudent);
        assertEquals(student, retrievedStudent);
    }

    @Test
    @Order(2)
    void shouldGetStudentById() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        assertEquals(student, studentDao.getStudentById(1));
    }

    @Test
    @Order(3)
    void shouldUpdateStudent() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        student.setGroupId(1);
        studentDao.updateStudent(student);

        Student updatedStudent = studentDao.getStudentById(1);

        assertNotNull(updatedStudent);
        assertEquals(student, updatedStudent);
    }

    @Test
    @Order(4)
    void shouldRemoveStudentById() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        studentDao.removeStudentById(1);

        assertNull(studentDao.getStudentById(1));
    }

    @Test
    void shouldGetAndAddAllItems() {
        List<Student> students = new ArrayList<>();
        students.add(getTestStudent(1, "John"));
        students.add(getTestStudent(2, "Jane"));
        students.add(getTestStudent(3, "Jim"));

        studentDao.addAllItems(students);

        List<Student> retrievedStudents = studentDao.getAllItems();

        assertEquals(students.size(), retrievedStudents.size());
        assertEquals(students, retrievedStudents);
    }

    private Student getTestStudent() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName("John");
        student.setLastName("Doe");

        return student;
    }

    private Student getTestStudent(int id, String name) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(name);
        student.setLastName("Doe");

        return student;
    }
}