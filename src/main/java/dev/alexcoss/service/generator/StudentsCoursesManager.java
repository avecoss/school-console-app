package dev.alexcoss.service.generator;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.dto.StudentDTO;
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

    public StudentsCoursesManager(CourseRandomizer courseRandomizer, StudentCourseService studentCourseService,
                                  StudentService studentService, CourseService courseService) {
        this.courseRandomizer = courseRandomizer;
        this.studentCourseService = studentCourseService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void assignStudentsToCoursesAndSave() {
        List<StudentDTO> studentsFromDatabase = studentService.getStudents();
        List<CourseDTO> coursesFromDatabase = courseService.getCourses();
        Map<Integer, Set<Integer>> mapStudentCourses = courseRandomizer.assignStudentsToCourse(studentsFromDatabase, coursesFromDatabase);
        studentCourseService.addAllStudentCourseRelationships(mapStudentCourses);
    }
}