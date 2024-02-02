package dev.alexcoss.service;

import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudentService.class})
class StudentServiceTest {
    @MockBean
    private StudentDao studentDao;

    @Autowired
    private StudentService studentService;

    @Test
    public void shouldGetStudentById() {
        int studentId = 1;
        when(studentDao.getStudentById(studentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        Optional<Student> student = studentService.getStudentById(studentId);

        assertTrue(student.isPresent());
        verify(studentDao).getStudentById(studentId);
    }

    @Test
    public void shouldGetStudentsByCourse() {
        String courseName = "Math";
        when(studentDao.getStudentsByCourse(courseName)).thenReturn(getSampleStudentList());

        List<Student> students = studentService.getStudentsByCourse(courseName);

        assertNotNull(students);
        verify(studentDao).getStudentsByCourse(courseName);
    }

    @Test
    public void shouldGetAllStudents() {
        when(studentDao.getAllItems()).thenReturn(getSampleStudentList());

        List<Student> students = studentService.getStudents();

        assertNotNull(students);
        verify(studentDao).getAllItems();
    }

    @Test
    public void shouldAddStudents() {
        List<Student> studentList = getSampleStudentList();
        studentService.addStudents(studentList);

        verify(studentDao, times(1)).addAllItems(studentList);
    }

    @Test
    public void shouldNotAddStudentsWhenListIsNull() {
        studentService.addStudents(null);

        verify(studentDao, never()).addAllItems(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListIsEmpty() {
        studentService.addStudents(Collections.emptyList());

        verify(studentDao, never()).addAllItems(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListContainsInvalidStudent() {
        List<Student> studentListWithInvalid = Arrays.asList(new Student(), null);
        studentService.addStudents(studentListWithInvalid);

        verify(studentDao, never()).addAllItems(studentListWithInvalid);
    }

    @Test
    public void shouldAddValidStudent() {
        Student validStudent = getStudent(1, "John", "Doe");
        studentService.addStudent(validStudent);

        verify(studentDao, times(1)).addItem(validStudent);
    }

    @Test
    public void shouldNotAddInvalidStudent() {
        Student invalidStudent = new Student();
        studentService.addStudent(invalidStudent);

        verify(studentDao, never()).addItem(invalidStudent);
    }

    @Test
    public void shouldRemoveExistingStudentById() {
        int existingStudentId = 1;
        when(studentDao.getStudentById(existingStudentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        studentService.removeStudentById(existingStudentId);

        verify(studentDao, times(1)).removeStudentById(existingStudentId);
    }

    @Test
    public void shouldNotRemoveNonExistingStudentById() {
        int nonExistingStudentId = 99;
        when(studentDao.getStudentById(nonExistingStudentId)).thenReturn(Optional.empty());

        studentService.removeStudentById(nonExistingStudentId);

        verify(studentDao, never()).removeStudentById(nonExistingStudentId);
    }

    private Student getStudent(int id, String firstName, String lastName) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        return student;
    }

    private List<Student> getSampleStudentList() {
        return Arrays.asList(getStudent(1, "John", "Doe"), getStudent(2, "Jane", "Smith"));
    }
}