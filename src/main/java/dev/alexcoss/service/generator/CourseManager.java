package dev.alexcoss.service.generator;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseManager {
    private final CourseService courseService;
    private final CoursesGenerator coursesGenerator;

    public CourseManager(CourseService courseService, CoursesGenerator coursesGenerator) {
        this.courseService = courseService;
        this.coursesGenerator = coursesGenerator;
    }

    public void generateAndSaveCoursesToDatabase() {
        List<CourseDTO> courses = coursesGenerator.getCoursesList();
        courseService.addCourses(courses);
    }
}
