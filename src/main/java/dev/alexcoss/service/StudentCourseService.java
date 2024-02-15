package dev.alexcoss.service;

import dev.alexcoss.dao.StudentsCoursesDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentsCoursesDao studentsCoursesRepository;

    public void addAllStudentCourseRelationships(Map<Integer, Set<Integer>> studentCourseMap) {
        if (isValidStudentCourseMap(studentCourseMap)) {
            studentsCoursesRepository.addAllItems(studentCourseMap);
            log.info("Added all student-course relationships to the database");
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        if (isValidId(studentId, "Student ID") && isValidId(courseId, "Course ID")) {
            Map<Integer, Integer> map = Collections.singletonMap(studentId, courseId);
            studentsCoursesRepository.addItem(map);
            log.info("Added student with ID {} to the course with ID {}", studentId, courseId);
        }
    }

    public void removeStudentFromCourse(int studentId, int courseId) {
        if (isValidId(studentId, "Student ID") && isValidId(courseId, "Course ID")) {
            Map<Integer, Integer> map = Collections.singletonMap(studentId, courseId);
            studentsCoursesRepository.removeItems(map);
            log.info("Removed student with ID {} from the course with ID {}", studentId, courseId);
        }
    }

    private boolean isValidStudentCourseMap(Map<Integer, Set<Integer>> studentCourseMap) {
        if (studentCourseMap == null || studentCourseMap.isEmpty()) {
            log.error("Student-course map is null or empty");
            return false;
        }
        studentCourseMap.forEach((studentId, courseSet) -> {
            if (!isValidId(studentId, "Student ID")) {
                return;
            }
            if (courseSet == null || courseSet.isEmpty()) {
                log.error("Course set for student ID {} is null or empty", studentId);
                return;
            }
            courseSet.forEach(courseId -> isValidId(courseId, "Course ID"));
        });
        return true;
    }

    private boolean isValidId(int id, String idName) {
        if (id <= 0) {
            log.error("{} is negative integer", idName);
            return false;
        }
        return true;
    }
}
