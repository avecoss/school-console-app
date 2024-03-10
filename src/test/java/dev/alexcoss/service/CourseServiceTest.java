package dev.alexcoss.service;

import dev.alexcoss.dao.JPACourseDao;
import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CourseService.class, ModelMapper.class})
class CourseServiceTest {
    @MockBean
    private JPACourseDao courseDao;

    @Autowired
    private CourseService courseService;

    @Test
    void shouldGetAllCourses() {
        List<Course> entityCourses = getSampleCourseEntityList();
        when(courseDao.findAllItems()).thenReturn(entityCourses);

        List<CourseDTO> expectedCourses = getSampleCourseDtoList();
        List<CourseDTO> actualCourses = courseService.getCourses();

        assertEquals(expectedCourses.size(), actualCourses.size());
        assertEquals(expectedCourses.get(0).getName(), actualCourses.get(0).getName());
        verify(courseDao).findAllItems();
    }

    @Test
    void shouldAddValidCourses() {
        List<CourseDTO> validCourses = getSampleCourseDtoList();

        courseService.addCourses(validCourses);

        verify(courseDao).saveAllItems(getSampleCourseEntityList());
    }

    @Test
    void shouldNotAddInvalidCourses() {
        List<CourseDTO> invalidCourses = Arrays.asList(
            new CourseDTO(1, "Math", "Math Description"),
            null,
            new CourseDTO(2, "Art", "Art Description")
        );

        courseService.addCourses(invalidCourses);

        verify(courseDao, never()).saveAllItems(anyList());
    }

    private Course getCourse(int id, String name, String description) {
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDescription(description);

        return course;
    }

    private List<Course> getSampleCourseEntityList() {
        return Arrays.asList(
            getCourse(1, "Math", "Math Description"),
            getCourse(2, "History", "History Description")
        );
    }

    private List<CourseDTO> getSampleCourseDtoList() {
        return Arrays.asList(
            new CourseDTO(1, "Math", "Math Description"),
            new CourseDTO(2, "History", "History Description")
        );
    }
}