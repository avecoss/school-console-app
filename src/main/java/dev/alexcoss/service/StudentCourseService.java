package dev.alexcoss.service;

import dev.alexcoss.dao.JPACourseDao;
import dev.alexcoss.dao.JPAStudentDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentCourseService {

    private final JPACourseDao courseDao;
    private final JPAStudentDao studentDao;

    @Transactional
    public void addAllStudentCourseRelationships(Map<Integer, Set<Integer>> studentCourseMap) {
        if (isValidStudentCourseMap(studentCourseMap)) {
            studentCourseMap.forEach((studentId, courseIds) -> {
                studentDao.findItemById(studentId).ifPresent(student -> {
                    courseIds.stream()
                        .map(courseDao::findItemById)
                        .flatMap(Optional::stream)
                        .forEach(course -> {
                            course.addStudentToCourse(student);
                        });
                });
            });
            log.info("Added all student-course relationships to the database");
        }
    }

    @Transactional
    public void addStudentToCourse(int studentId, int courseId) {
        if (isValidId(studentId, "Student ID") && isValidId(courseId, "Course ID")) {
            Optional<Student> studentById = studentDao.findItemById(studentId);
            Optional<Course> courseById = courseDao.findItemById(courseId);

            if (studentById.isPresent() && courseById.isPresent()) {
                courseById.get().addStudentToCourse(studentById.get());
                log.info("Added student with ID {} to the course with ID {}", studentId, courseId);
            }
        }
    }

    @Transactional
    public void removeStudentFromCourse(int studentId, int courseId) {
        if (isValidId(studentId, "Student ID") && isValidId(courseId, "Course ID")) {
            Optional<Student> studentById = studentDao.findItemById(studentId);
            Optional<Course> courseById = courseDao.findItemById(courseId);

            if (studentById.isPresent() && courseById.isPresent()) {
                courseById.get().removeStudentFromCourse(studentById.get());
                log.info("Removed student with ID {} from the course with ID {}", studentId, courseId);
            }
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
