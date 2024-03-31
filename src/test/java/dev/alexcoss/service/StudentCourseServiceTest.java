package dev.alexcoss.service;

import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;
import dev.alexcoss.repository.CourseRepository;
import dev.alexcoss.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudentCourseService.class})
class StudentCourseServiceTest {
    @Autowired
    private StudentCourseService studentCourseService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void shouldAddAllStudentCourseRelationships() {
        Map<Integer, Set<Integer>> studentCourseMap = getSampleStudentCourseMap();

        Student student1 = Student.builder().id(1).firstName("John").lastName("Doe").build();
        Student student2 = Student.builder().id(2).firstName("Alice").lastName("Tacker").build();

        Course course30 = Course.builder().id(30).name("Math").build();
        Course course31 = Course.builder().id(31).name("History").build();
        Course course33 = Course.builder().id(33).name("Physics").build();

        when(studentRepository.findById(1)).thenReturn(Optional.of(student1));
        when(studentRepository.findById(2)).thenReturn(Optional.of(student2));
        when(courseRepository.findById(30)).thenReturn(Optional.of(course30));
        when(courseRepository.findById(31)).thenReturn(Optional.of(course31));
        when(courseRepository.findById(33)).thenReturn(Optional.of(course33));

        studentCourseService.addAllStudentCourseRelationships(studentCourseMap);

        verify(studentRepository, times(2)).findById(anyInt());
        verify(courseRepository, times(4)).findById(anyInt());
    }

    @Test
    public void shouldNotAddAllStudentCourseRelationshipsWhenMapIsNull() {
        studentCourseService.addAllStudentCourseRelationships(null);

        verify(courseRepository, never()).findById(anyInt());
        verify(studentRepository, never()).findById(anyInt());
    }

    @Test
    public void shouldNotAddAllStudentCourseRelationshipsWhenMapIsEmpty() {
        studentCourseService.addAllStudentCourseRelationships(Collections.emptyMap());

        verify(courseRepository, never()).findById(anyInt());
        verify(studentRepository, never()).findById(anyInt());
    }

    @Test
    public void shouldAddStudentToCourse() {
        int studentId = 1;
        int courseId = 30;
        Student student = Student.builder().id(studentId).build();
        Course course = Course.builder().id(courseId).build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentCourseService.addStudentToCourse(studentId, courseId);

        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
    }

    @Test
    public void shouldNotAddStudentToCourseWhenStudentIdIsInvalid() {
        studentCourseService.addStudentToCourse(-1, 30);

        verify(studentRepository, never()).findById(anyInt());
        verify(courseRepository, never()).findById(anyInt());
    }

    @Test
    public void shouldNotAddStudentToCourseWhenCourseIdIsInvalid() {
        studentCourseService.addStudentToCourse(1, -30);

        verify(studentRepository, never()).findById(anyInt());
        verify(courseRepository, never()).findById(anyInt());
    }

    @Test
    public void shouldRemoveStudentFromCourse() {
        int studentId = 1;
        int courseId = 30;
        Student student = Student.builder().id(studentId).build();
        Course course = Course.builder().id(courseId).build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentCourseService.removeStudentFromCourse(studentId, courseId);

        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
    }

    @Test
    public void shouldNotRemoveStudentFromCourseWhenStudentIdIsInvalid() {
        studentCourseService.removeStudentFromCourse(-1, 30);

        verify(studentRepository, never()).findById(anyInt());
        verify(courseRepository, never()).findById(anyInt());
    }

    @Test
    public void shouldNotRemoveStudentFromCourseWhenCourseIdIsInvalid() {
        studentCourseService.removeStudentFromCourse(1, -30);

        verify(studentRepository, never()).findById(anyInt());
        verify(courseRepository, never()).findById(anyInt());
    }

    private Map<Integer, Set<Integer>> getSampleStudentCourseMap() {
        Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();
        studentCourseMap.put(1, new HashSet<>(Arrays.asList(30, 31)));
        studentCourseMap.put(2, new HashSet<>(Arrays.asList(30, 33)));

        return studentCourseMap;
    }
}