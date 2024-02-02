package dev.alexcoss.service.generator;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dao.StudentsCoursesDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;
import dev.alexcoss.service.CourseService;
import dev.alexcoss.service.StudentCourseService;
import dev.alexcoss.service.StudentService;
import dev.alexcoss.service.generator.rangomizer.CourseRandomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StudentsCoursesManager {
    private final CourseRandomizer courseRandomizer;
    private final StudentCourseService studentCourseService;
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentsCoursesManager(CourseRandomizer courseRandomizer, StudentCourseService studentCourseService,
                                  StudentService studentService, CourseService courseService) {
        this.courseRandomizer = courseRandomizer;
        this.studentCourseService = studentCourseService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void assignStudentsToCoursesAndSave() {
        List<Student> studentsFromDatabase = getStudentsFromDatabase();
        List<Course> coursesFromDatabase = getCoursesFromDatabase();
        Map<Integer, Set<Integer>> mapStudentCourses = assignStudentsToCourse(studentsFromDatabase, coursesFromDatabase);
        saveCoursesToDatabase(mapStudentCourses);
    }

    private List<Student> getStudentsFromDatabase() {
        return studentService.getStudents();
    }

    private List<Course> getCoursesFromDatabase() {
        return courseService.getCourses();
    }

    private Map<Integer, Set<Integer>> assignStudentsToCourse(List<Student> students, List<Course> courses) {
        return courseRandomizer.assignStudentsToCourse(students, courses);
    }

    private void saveCoursesToDatabase(Map<Integer, Set<Integer>> mapStudentCourses) {
        studentCourseService.addAllStudentCourseRelationships(mapStudentCourses);
    }
}
