package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CourseService.class})
class CourseServiceTest {
    @MockBean
    private CourseDao courseDao;

    @Autowired
    private CourseService courseService;

    @Test
    void shouldGetAllCourses() {
        List<Course> expectedCourses = Arrays.asList(
            getCourse(1, "Math", "Math Description"),
            getCourse(2, "History", "History Description")
        );
        when(courseDao.getAllItems()).thenReturn(expectedCourses);

        List<Course> actualCourses = courseService.getCourses();

        assertEquals(expectedCourses, actualCourses);
        verify(courseDao).getAllItems();
    }

    @Test
    void shouldAddValidCourses() {
        List<Course> validCourses = Arrays.asList(
            getCourse(1, "Math", "Math Description"),
            getCourse(2, "History", "History Description")
        );

        courseService.addCourses(validCourses);

        verify(courseDao).addAllItems(validCourses);
    }

    @Test
    void shouldNotAddInvalidCourses() {
        List<Course> invalidCourses = Arrays.asList(
            getCourse(1, "Math", "Math Description"),
            null,
            getCourse(3, "Art", "Art Description")
        );

        courseService.addCourses(invalidCourses);

        verify(courseDao, never()).addAllItems(anyList());
    }

    private Course getCourse(int id, String name, String description) {
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDescription(description);

        return course;
    }
}