package dev.alexcoss.service;

import dev.alexcoss.dao.StudentsCoursesDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudentCourseService.class})
class StudentCourseServiceTest {
    @MockBean
    private StudentsCoursesDao studentsCoursesDao;

    @Autowired
    private StudentCourseService studentCourseService;

    @Test
    public void shouldAddAllStudentCourseRelationships() {
        Map<Integer, Set<Integer>> studentCourseMap = getSampleStudentCourseMap();
        studentCourseService.addAllStudentCourseRelationships(studentCourseMap);

        verify(studentsCoursesDao, times(1)).addAllItems(studentCourseMap);
    }

    @Test
    public void shouldNotAddAllStudentCourseRelationshipsWhenMapIsNull() {
        studentCourseService.addAllStudentCourseRelationships(null);

        verify(studentsCoursesDao, never()).addAllItems(anyMap());
    }

    @Test
    public void shouldNotAddAllStudentCourseRelationshipsWhenMapIsEmpty() {
        studentCourseService.addAllStudentCourseRelationships(Collections.emptyMap());

        verify(studentsCoursesDao, never()).addAllItems(anyMap());
    }

    @Test
    public void shouldAddStudentToCourse() {
        int studentId = 1;
        int courseId = 30;
        studentCourseService.addStudentToCourse(studentId, courseId);

        verify(studentsCoursesDao, times(1)).addItem(Collections.singletonMap(studentId, courseId));
    }

    @Test
    public void shouldNotAddStudentToCourseWhenStudentIdIsInvalid() {
        studentCourseService.addStudentToCourse(-1, 30);

        verify(studentsCoursesDao, never()).addItem(anyMap());
    }

    @Test
    public void shouldNotAddStudentToCourseWhenCourseIdIsInvalid() {
        studentCourseService.addStudentToCourse(1, -30);

        verify(studentsCoursesDao, never()).addItem(anyMap());
    }

    @Test
    public void shouldRemoveStudentFromCourse() {
        int studentId = 1;
        int courseId = 30;
        studentCourseService.removeStudentFromCourse(studentId, courseId);

        verify(studentsCoursesDao, times(1)).removeItems(Collections.singletonMap(studentId, courseId));
    }

    @Test
    public void shouldNotRemoveStudentFromCourseWhenStudentIdIsInvalid() {
        studentCourseService.removeStudentFromCourse(-1, 30);

        verify(studentsCoursesDao, never()).removeItems(anyMap());
    }

    @Test
    public void shouldNotRemoveStudentFromCourseWhenCourseIdIsInvalid() {
        studentCourseService.removeStudentFromCourse(1, -30);

        verify(studentsCoursesDao, never()).removeItems(anyMap());
    }

    private Map<Integer, Set<Integer>> getSampleStudentCourseMap() {
        Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();
        studentCourseMap.put(1, new HashSet<>(Arrays.asList(30, 31)));
        studentCourseMap.put(2, new HashSet<>(Arrays.asList(30, 33)));

        return studentCourseMap;
    }
}