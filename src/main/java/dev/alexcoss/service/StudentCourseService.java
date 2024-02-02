package dev.alexcoss.service;

import dev.alexcoss.dao.StudentsCoursesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
public class StudentCourseService extends AbstractService {

    private StudentsCoursesDao studentsCoursesRepository;

    @Autowired
    public StudentCourseService(StudentsCoursesDao studentsCoursesRepository) {
        super(StudentCourseService.class.getName());
        this.studentsCoursesRepository = studentsCoursesRepository;
    }

    public void addAllStudentCourseRelationships(Map<Integer, Set<Integer>> studentCourseMap) {
        if (isValidStudentCourseMap(studentCourseMap)) {
            try {
                studentsCoursesRepository.addAllItems(studentCourseMap);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error adding student-course relationships to database");
            }
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        if (isValidId(studentId, "Student ID") && isValidId(courseId, "Course ID")) {
            try {
                Map<Integer, Integer> map = Collections.singletonMap(studentId, courseId);
                studentsCoursesRepository.addItem(map);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error adding student to course in database");
            }
        }
    }

    public void removeStudentFromCourse(int studentId, int courseId) {
        if (isValidId(studentId, "Student ID") && isValidId(courseId, "Course ID")) {
            try {
                Map<Integer, Integer> map = Collections.singletonMap(studentId, courseId);
                studentsCoursesRepository.removeItems(map);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error removing student from course in database");
            }
        }
    }

    private boolean isValidStudentCourseMap(Map<Integer, Set<Integer>> studentCourseMap) {
        if (studentCourseMap == null || studentCourseMap.isEmpty()) {
            handleServiceException("Student-course map is null or empty");
            return false;
        }
        studentCourseMap.forEach((studentId, courseSet) -> {
            if (!isValidId(studentId, "Student ID")) {
                return;
            }
            if (courseSet == null || courseSet.isEmpty()) {
                handleServiceException("Course set for student ID " + studentId + " is null or empty");
                return;
            }
            courseSet.forEach(courseId -> isValidId(courseId, "Course ID"));
        });
        return true;
    }

    private boolean isValidId(int id, String idName) {
        if (id <= 0) {
            handleServiceException(idName + " is negative integer");
            return false;
        }
        return true;
    }
}
