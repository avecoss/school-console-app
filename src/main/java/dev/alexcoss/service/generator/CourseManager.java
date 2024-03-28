package dev.alexcoss.service.generator;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CourseManager {
    private final CourseService courseService;
    private final CoursesGenerator coursesGenerator;

    public void generateAndSaveCoursesToDatabase() {
        List<CourseDTO> courses = coursesGenerator.getCoursesList();
        courseService.addCourses(courses);
    }
}
