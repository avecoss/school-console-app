package dev.alexcoss.service.generator;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.service.CourseService;
import dev.alexcoss.service.StudentCourseService;
import dev.alexcoss.service.StudentService;
import dev.alexcoss.service.generator.rangomizer.CourseRandomizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class StudentsCoursesManager {
    private final CourseRandomizer courseRandomizer;
    private final StudentCourseService studentCourseService;
    private final StudentService studentService;
    private final CourseService courseService;

    public void assignStudentsToCoursesAndSave() {
        List<StudentDTO> studentsFromDatabase = studentService.getStudents();
        List<CourseDTO> coursesFromDatabase = courseService.getCourses();
        Map<Integer, Set<Integer>> mapStudentCourses = courseRandomizer.assignStudentsToCourse(studentsFromDatabase, coursesFromDatabase);

        if (!mapStudentCourses.isEmpty()) {
            studentCourseService.addAllStudentCourseRelationships(mapStudentCourses);
        } else {
            log.warn("No students assigned to courses");
        }
    }
}
