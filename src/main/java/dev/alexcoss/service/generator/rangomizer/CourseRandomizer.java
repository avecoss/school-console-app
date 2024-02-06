package dev.alexcoss.service.generator.rangomizer;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.dto.StudentDTO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CourseRandomizer extends Randomizer {

    private static final int MIN_COURSES = 1;
    private static final int MAX_COURSES = 3;

    public Map<Integer, Set<Integer>> assignStudentsToCourse(List<StudentDTO> students, List<CourseDTO> courses) {
        shuffleCollections(students, courses);

        Map<Integer, Set<Integer>> studentCourseAssignment = new HashMap<>();

        for (StudentDTO student : students) {
            int quantityCourses = getRandomInteger(MAX_COURSES, MIN_COURSES);
            Set<Integer> studentCourses = getRandomCourses(courses, quantityCourses);
            studentCourseAssignment.put(student.getId(), studentCourses);
        }

        return studentCourseAssignment;
    }

    private Set<Integer> getRandomCourses(List<CourseDTO> courses, int quantity) {
        Set<Integer> studentCourses = new HashSet<>();
        int max = courses.size();

        quantity = Math.min(quantity, max);

        while (studentCourses.size() < quantity) {
            studentCourses.add(getRandomInteger(max, getMinCourseId(courses)));
        }
        return studentCourses;
    }

    private int getMinCourseId(List<CourseDTO> courses) {
        return courses.stream()
            .mapToInt(CourseDTO::getId)
            .min()
            .orElseThrow(NoSuchElementException::new);
    }
}
