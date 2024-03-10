package dev.alexcoss.service;

import dev.alexcoss.dao.JPAStudentDao;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudentService.class, ModelMapper.class})
class StudentServiceTest {
    @MockBean
    private JPAStudentDao studentDao;

    @Autowired
    private StudentService studentService;

    @Test
    public void shouldGetStudentById() {
        int studentId = 1;
        when(studentDao.findItemById(studentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        Optional<StudentDTO> student = studentService.getStudentById(studentId);

        assertTrue(student.isPresent());
        verify(studentDao).findItemById(studentId);
    }

    @Test
    public void shouldGetStudentsByCourse() {
        String courseName = "Math";
        when(studentDao.findStudentsByCourse(courseName)).thenReturn(getSampleStudentEntityList());

        List<StudentDTO> students = studentService.getStudentsByCourse(courseName);

        assertNotNull(students);
        verify(studentDao).findStudentsByCourse(courseName);
    }

    @Test
    public void shouldGetAllStudents() {
        when(studentDao.findAllItems()).thenReturn(getSampleStudentEntityList());

        List<StudentDTO> students = studentService.getStudents();

        assertNotNull(students);
        verify(studentDao).findAllItems();
    }

    @Test
    public void shouldAddStudents() {
        List<StudentDTO> studentList = getSampleStudentDtoList();
        studentService.addStudents(studentList);

        verify(studentDao, times(1)).saveAllItems(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListIsNull() {
        studentService.addStudents(null);

        verify(studentDao, never()).saveAllItems(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListIsEmpty() {
        studentService.addStudents(Collections.emptyList());

        verify(studentDao, never()).saveAllItems(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListContainsInvalidStudent() {
        List<StudentDTO> studentListWithInvalid = Arrays.asList(new StudentDTO(), null);
        studentService.addStudents(studentListWithInvalid);

        verify(studentDao, never()).saveAllItems(anyList());
    }

    @Test
    public void shouldAddValidStudent() {
        StudentDTO validStudent = StudentDTO.builder().id(1).firstName("John").lastName("Doe").build();
        studentService.addStudent(validStudent);

        verify(studentDao, times(1)).saveItem(getStudent(1, "John", "Doe"));
    }

    @Test
    public void shouldNotAddInvalidStudent() {
        StudentDTO invalidStudent = new StudentDTO();
        studentService.addStudent(invalidStudent);

        verify(studentDao, never()).saveItem(any());
    }

    @Test
    public void shouldRemoveExistingStudentById() {
        int existingStudentId = 1;
        when(studentDao.findItemById(existingStudentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        studentService.removeStudentById(existingStudentId);

        verify(studentDao, times(1)).deleteItemById(existingStudentId);
    }

    @Test
    public void shouldNotRemoveNonExistingStudentById() {
        int nonExistingStudentId = 99;
        when(studentDao.findItemById(nonExistingStudentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            studentService.removeStudentById(nonExistingStudentId);
        });
        verify(studentDao, never()).deleteItemById(nonExistingStudentId);
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
        return Arrays.asList(
            StudentDTO.builder().id(1).firstName("John").lastName("Doe").build(),
            StudentDTO.builder().id(2).firstName("Jane").lastName("Smith").build()
        );
    }
}