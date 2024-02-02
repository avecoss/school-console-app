package dev.alexcoss.service.generator;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseManager {
    private final CourseService courseService;
    private final CoursesGenerator coursesGenerator;

    @Autowired
    public CourseManager(CourseService courseService, CoursesGenerator coursesGenerator) {
        this.courseService = courseService;
        this.coursesGenerator = coursesGenerator;
    }

    public void generateAndSaveCoursesToDatabase() {
        List<Course> courses = generateCourses();
        saveCoursesToDatabase(courses);
    }

    private List<Course> generateCourses() {
        return coursesGenerator.getCoursesList();
    }

    private void saveCoursesToDatabase(List<Course> courses) {
        courseService.addCourses(courses);
    }
}
