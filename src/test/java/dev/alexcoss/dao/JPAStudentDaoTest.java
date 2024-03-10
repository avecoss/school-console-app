package dev.alexcoss.dao;

import dev.alexcoss.model.Student;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JPAStudentDao.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPAStudentDaoTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private JPAStudentDao studentDao;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldAddItem() {
        Student student = Student.builder().firstName("John").lastName("Doe").build();
        studentDao.saveItem(student);

        Optional<Student> retrievedStudent = studentDao.findItemById(1);

        assertTrue(retrievedStudent.isPresent());
        assertEquals(student, retrievedStudent.get());
    }

    @Test
    void shouldGetStudentById() {
        Student student = Student.builder().firstName("John").lastName("Doe").build();
        studentDao.saveItem(student);

        Optional<Student> retrievedStudent = studentDao.findItemById(1);

        assertTrue(retrievedStudent.isPresent());
        assertEquals(student, retrievedStudent.get());
    }

    @Test
    void shouldUpdateStudent() {
        Student student = Student.builder().firstName("John").lastName("Doe").build();
        studentDao.saveItem(student);

        student.setFirstName("firstName");
        studentDao.updateItem(student);

        Optional<Student> updatedStudent = studentDao.findItemById(1);

        assertTrue(updatedStudent.isPresent());
        assertEquals(student, updatedStudent.get());
    }

    @Test
    void shouldRemoveStudentById() {
        Student student = Student.builder().firstName("John").lastName("Doe").build();
        studentDao.saveItem(student);

        studentDao.deleteItemById(1);

        Optional<Student> removedStudent = studentDao.findItemById(1);

        assertFalse(removedStudent.isPresent());
    }

    @Test
    void shouldGetAndAddAllItems() {
        List<Student> students = new ArrayList<>();
        students.add(Student.builder().firstName("John").lastName("Doe").build());
        students.add(Student.builder().firstName("Jane").lastName("Doe").build());
        students.add(Student.builder().firstName("Jim").lastName("Doe").build());

        studentDao.saveAllItems(students);

        List<Student> retrievedStudents = studentDao.findAllItems();

        assertEquals(students.size(), retrievedStudents.size());
        assertEquals(students, retrievedStudents);
    }
}