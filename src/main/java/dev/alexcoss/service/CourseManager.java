package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.model.Course;

import java.util.List;

public class CourseManager {
    private final CourseDao courseDao;
    private final CoursesGenerator coursesGenerator;

    public CourseManager(CourseDao courseDao, CoursesGenerator coursesGenerator) {
        this.courseDao = courseDao;
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
        courseDao.addAllItems(courses);
    }
}
