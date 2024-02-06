package dev.alexcoss.service;

import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudentService.class})
@Import({MapperConfig.class})
class StudentServiceTest {
    @MockBean
    private StudentDao studentDao;

    @Autowired
    private StudentService studentService;

    @Test
    public void shouldGetStudentById() {
        int studentId = 1;
        when(studentDao.getStudentById(studentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        Optional<StudentDTO> student = studentService.getStudentById(studentId);

        assertTrue(student.isPresent());
        verify(studentDao).getStudentById(studentId);
    }

    @Test
    public void shouldGetStudentsByCourse() {
        String courseName = "Math";
        when(studentDao.getStudentsByCourse(courseName)).thenReturn(getSampleStudentEntityList());

        List<StudentDTO> students = studentService.getStudentsByCourse(courseName);

        assertNotNull(students);
        verify(studentDao).getStudentsByCourse(courseName);
    }

    @Test
    public void shouldGetAllStudents() {
        when(studentDao.getAllItems()).thenReturn(getSampleStudentEntityList());

        List<StudentDTO> students = studentService.getStudents();

        assertNotNull(students);
        verify(studentDao).getAllItems();
    }

    @Test
    public void shouldAddStudents() {
        List<StudentDTO> studentList = getSampleStudentDtoList();
        studentService.addStudents(studentList);

        verify(studentDao, times(1)).addAllItems(anyList());
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
        List<StudentDTO> studentListWithInvalid = Arrays.asList(new StudentDTO(), null);
        studentService.addStudents(studentListWithInvalid);

        verify(studentDao, never()).addAllItems(anyList());
    }

    @Test
    public void shouldAddValidStudent() {
        StudentDTO validStudent = new StudentDTO(1, "John", "Doe");
        studentService.addStudent(validStudent);

        verify(studentDao, times(1)).addItem(getStudent(1, "John", "Doe"));
    }

    @Test
    public void shouldNotAddInvalidStudent() {
        StudentDTO invalidStudent = new StudentDTO();
        studentService.addStudent(invalidStudent);

        verify(studentDao, never()).addItem(any());
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

        assertThrows(NoSuchElementException.class, () -> {
            studentService.removeStudentById(nonExistingStudentId);
        });
        verify(studentDao, never()).removeStudentById(nonExistingStudentId);
    }

    private Student getStudent(int id, String firstName, String lastName) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        return student;
    }

    private List<Student> getSampleStudentEntityList() {
        return Arrays.asList(getStudent(1, "John", "Doe"), getStudent(2, "Jane", "Smith"));
    }

    private List<StudentDTO> getSampleStudentDtoList() {
        return Arrays.asList(new StudentDTO(1, "John", "Doe"), new StudentDTO(2, "Jane", "Smith"));
    }
}