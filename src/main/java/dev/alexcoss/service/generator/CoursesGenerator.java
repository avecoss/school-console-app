package dev.alexcoss.service.generator;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import dev.alexcoss.util.FileReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoursesGenerator {
    private static final String COURSES_PATH = "src/main/resources/data/courses.txt";

    public List<CourseDTO> getCoursesList() {
        return readList().stream()
            .map(this::createCourse)
            .collect(Collectors.toList());
    }

    private CourseDTO createCourse(String name) {
        return new CourseDTO(name);
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
