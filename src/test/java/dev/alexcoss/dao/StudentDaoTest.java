package dev.alexcoss.dao;

import dev.alexcoss.model.Student;
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

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Flyway flyway;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        flyway.clean();
        studentDao = new StudentDao(jdbcTemplate);
        flyway.migrate();
    }

    @Test
    void shouldAddItem() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        Student retrievedStudent = studentDao.getStudentById(1);

        assertNotNull(retrievedStudent);
        assertEquals(student, retrievedStudent);
    }

    @Test
    void shouldGetStudentById() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        assertEquals(student, studentDao.getStudentById(1));
    }

    @Test
    void shouldUpdateStudent() {
        Student student = getTestStudent();
        studentDao.addItem(student);

        student.setFirstName("firstName");
        studentDao.updateStudent(student);

        Student updatedStudent = studentDao.getStudentById(1);

        assertNotNull(updatedStudent);
        assertEquals(student, updatedStudent);
    }

    @Test
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