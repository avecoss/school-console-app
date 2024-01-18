package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dao.StudentsCoursesDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentsCoursesManager {
    private final CourseRandomizer courseRandomizer;
    private final StudentsCoursesDao studentsCoursesDao;
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentsCoursesManager(CourseRandomizer courseRandomizer, StudentsCoursesDao studentsCoursesDao,
                                  StudentDao studentDao, CourseDao courseDao) {

        this.courseRandomizer = courseRandomizer;
        this.studentsCoursesDao = studentsCoursesDao;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    public void assignStudentsToCoursesAndSave() {
        List<Student> studentsFromDatabase = getStudentsFromDatabase();
        List<Course> coursesFromDatabase = getCoursesFromDatabase();
        Map<Integer, Set<Integer>> mapStudentCourses = assignStudentsToCourse(studentsFromDatabase, coursesFromDatabase);
        saveCoursesToDatabase(mapStudentCourses);
    }

    private List<Student> getStudentsFromDatabase() {
        return studentDao.getAllItems();
    }

    private List<Course> getCoursesFromDatabase() {
        return courseDao.getAllItems();
    }

    private Map<Integer, Set<Integer>> assignStudentsToCourse(List<Student> students, List<Course> courses) {
        return courseRandomizer.assignStudentsToCourse(students, courses);
    }

    private void saveCoursesToDatabase(Map<Integer, Set<Integer>> mapStudentCourses) {
        studentsCoursesDao.addAllItems(mapStudentCourses);
    }
}
