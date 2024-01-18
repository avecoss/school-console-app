package dev.alexcoss.service;

import dev.alexcoss.model.Course;
import dev.alexcoss.util.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoursesGenerator {
    private static final String COURSES_PATH = "src/main/resources/data/courses.txt";

    public List<Course> getCoursesList() {
        return readList().stream()
            .map(this::createCourse)
            .collect(Collectors.toList());
    }

    private Course createCourse(String name) {
        Course course = new Course();

        course.setName(name);
        return course;
    }

    private List<String> readList() {
        FileReader reader = new FileReader();
        return reader.fileRead(COURSES_PATH, bufferedReader -> {
            List<String> list = new ArrayList<>();
            bufferedReader.lines().forEach(list::add);
            return list;
        });
    }
}
