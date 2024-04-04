package dev.alexcoss.service;

import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import dev.alexcoss.repository.StudentRepository;
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
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Test
    public void shouldGetStudentById() {
        int studentId = 1;
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        Optional<StudentDTO> student = studentService.getStudentById(studentId);

        assertTrue(student.isPresent());
        verify(studentRepository).findById(studentId);
    }

    @Test
    public void shouldGetStudentsByCourse() {
        String courseName = "Math";
        when(studentRepository.findByCoursesName(courseName)).thenReturn(getSampleStudentEntityList());

        List<StudentDTO> students = studentService.getStudentsByCourse(courseName);

        assertNotNull(students);
        verify(studentRepository).findByCoursesName(courseName);
    }

    @Test
    public void shouldGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(getSampleStudentEntityList());

        List<StudentDTO> students = studentService.getStudents();

        assertNotNull(students);
        verify(studentRepository).findAll();
    }

    @Test
    public void shouldAddStudents() {
        List<StudentDTO> studentList = getSampleStudentDtoList();
        studentService.addStudents(studentList);

        verify(studentRepository, times(1)).saveAllAndFlush(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListIsNull() {
        studentService.addStudents(null);

        verify(studentRepository, never()).saveAllAndFlush(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListIsEmpty() {
        studentService.addStudents(Collections.emptyList());

        verify(studentRepository, never()).saveAllAndFlush(anyList());
    }

    @Test
    public void shouldNotAddStudentsWhenListContainsInvalidStudent() {
        List<StudentDTO> studentListWithInvalid = Arrays.asList(new StudentDTO(), null);
        studentService.addStudents(studentListWithInvalid);

        verify(studentRepository, never()).saveAllAndFlush(anyList());
    }

    @Test
    public void shouldAddValidStudent() {
        StudentDTO validStudent = StudentDTO.builder().id(1).firstName("John").lastName("Doe").build();
        studentService.addStudent(validStudent);

        verify(studentRepository, times(1)).save(getStudent(1, "John", "Doe"));
    }

    @Test
    public void shouldNotAddInvalidStudent() {
        StudentDTO invalidStudent = new StudentDTO();
        studentService.addStudent(invalidStudent);

        verify(studentRepository, never()).save(any());
    }

    @Test
    public void shouldRemoveExistingStudentById() {
        int existingStudentId = 1;
        when(studentRepository.findById(existingStudentId)).thenReturn(Optional.of(getStudent(1, "John", "Doe")));

        studentService.removeStudentById(existingStudentId);

        verify(studentRepository, times(1)).deleteById(existingStudentId);
    }

    @Test
    public void shouldNotRemoveNonExistingStudentById() {
        int nonExistingStudentId = 99;
        when(studentRepository.findById(nonExistingStudentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            studentService.removeStudentById(nonExistingStudentId);
        });
        verify(studentRepository, never()).deleteById(nonExistingStudentId);
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