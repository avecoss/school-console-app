package dev.alexcoss.service;

import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.util.*;

public class CourseRandomizer extends Randomizer {

    private static final int MIN_COURSES = 1;
    private static final int MAX_COURSES = 3;

    public Map<Integer, Set<Integer>> assignStudentsToCourse(List<Student> students, List<Course> courses) {
        shuffleCollections(students, courses);

        Map<Integer, Set<Integer>> studentCourseAssignment = new HashMap<>();

        for (Student student : students) {
            int quantityCourses = getRandomInteger(MAX_COURSES, MIN_COURSES);
            Set<Integer> studentCourses = getRandomCourses(courses, quantityCourses);
            studentCourseAssignment.put(student.getId(), studentCourses);
        }

        return studentCourseAssignment;
    }

    private Set<Integer> getRandomCourses(List<Course> courses, int quantity) {
        Set<Integer> studentCourses = new HashSet<>();
        int max = courses.size();

        quantity = Math.min(quantity, max);

        while (studentCourses.size() < quantity) {
            studentCourses.add(getRandomInteger(max, getMinCourseId(courses)));
        }
        return studentCourses;
    }

    private int getMinCourseId(List<Course> courses) {
        return courses.stream()
            .mapToInt(Course::getId)
            .min()
            .orElseThrow(NoSuchElementException::new);
    }
}
