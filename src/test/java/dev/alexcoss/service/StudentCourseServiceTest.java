package dev.alexcoss.service;

import dev.alexcoss.dao.JPACourseDao;
import dev.alexcoss.dao.JPAStudentDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;
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
    private JPACourseDao courseDao;

    @MockBean
    private JPAStudentDao studentDao;

    @Test
    public void shouldAddAllStudentCourseRelationships() {
        Map<Integer, Set<Integer>> studentCourseMap = getSampleStudentCourseMap();

        Student student1 = Student.builder().id(1).firstName("John").lastName("Doe").build();
        Student student2 = Student.builder().id(2).firstName("Alice").lastName("Tacker").build();

        Course course30 = Course.builder().id(30).name("Math").build();
        Course course31 = Course.builder().id(31).name("History").build();
        Course course33 = Course.builder().id(33).name("Physics").build();

        when(studentDao.findItemById(1)).thenReturn(Optional.of(student1));
        when(studentDao.findItemById(2)).thenReturn(Optional.of(student2));
        when(courseDao.findItemById(30)).thenReturn(Optional.of(course30));
        when(courseDao.findItemById(31)).thenReturn(Optional.of(course31));
        when(courseDao.findItemById(33)).thenReturn(Optional.of(course33));

        studentCourseService.addAllStudentCourseRelationships(studentCourseMap);

        verify(studentDao, times(2)).findItemById(anyInt());
        verify(courseDao, times(4)).findItemById(anyInt());
    }

    @Test
    public void shouldNotAddAllStudentCourseRelationshipsWhenMapIsNull() {
        studentCourseService.addAllStudentCourseRelationships(null);

        verify(courseDao, never()).findItemById(anyInt());
        verify(studentDao, never()).findItemById(anyInt());
    }

    @Test
    public void shouldNotAddAllStudentCourseRelationshipsWhenMapIsEmpty() {
        studentCourseService.addAllStudentCourseRelationships(Collections.emptyMap());

        verify(courseDao, never()).findItemById(anyInt());
        verify(studentDao, never()).findItemById(anyInt());
    }

    @Test
    public void shouldAddStudentToCourse() {
        int studentId = 1;
        int courseId = 30;
        Student student = Student.builder().id(studentId).build();
        Course course = Course.builder().id(courseId).build();

        when(studentDao.findItemById(studentId)).thenReturn(Optional.of(student));
        when(courseDao.findItemById(courseId)).thenReturn(Optional.of(course));

        studentCourseService.addStudentToCourse(studentId, courseId);

        verify(studentDao).findItemById(studentId);
        verify(courseDao).findItemById(courseId);
    }

    @Test
    public void shouldNotAddStudentToCourseWhenStudentIdIsInvalid() {
        studentCourseService.addStudentToCourse(-1, 30);

        verify(studentDao, never()).findItemById(anyInt());
        verify(courseDao, never()).findItemById(anyInt());
    }

    @Test
    public void shouldNotAddStudentToCourseWhenCourseIdIsInvalid() {
        studentCourseService.addStudentToCourse(1, -30);

        verify(studentDao, never()).findItemById(anyInt());
        verify(courseDao, never()).findItemById(anyInt());
    }

    @Test
    public void shouldRemoveStudentFromCourse() {
        int studentId = 1;
        int courseId = 30;
        Student student = Student.builder().id(studentId).build();
        Course course = Course.builder().id(courseId).build();

        when(studentDao.findItemById(studentId)).thenReturn(Optional.of(student));
        when(courseDao.findItemById(courseId)).thenReturn(Optional.of(course));

        studentCourseService.removeStudentFromCourse(studentId, courseId);

        verify(studentDao).findItemById(studentId);
        verify(courseDao).findItemById(courseId);
    }

    @Test
    public void shouldNotRemoveStudentFromCourseWhenStudentIdIsInvalid() {
        studentCourseService.removeStudentFromCourse(-1, 30);

        verify(studentDao, never()).findItemById(anyInt());
        verify(courseDao, never()).findItemById(anyInt());
    }

    @Test
    public void shouldNotRemoveStudentFromCourseWhenCourseIdIsInvalid() {
        studentCourseService.removeStudentFromCourse(1, -30);

        verify(studentDao, never()).findItemById(anyInt());
        verify(courseDao, never()).findItemById(anyInt());
    }

    private Map<Integer, Set<Integer>> getSampleStudentCourseMap() {
        Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();
        studentCourseMap.put(1, new HashSet<>(Arrays.asList(30, 31)));
        studentCourseMap.put(2, new HashSet<>(Arrays.asList(30, 33)));

        return studentCourseMap;
    }
}