package dev.alexcoss.console.actions;

import dev.alexcoss.dto.CourseDTO;

import java.util.List;

public class CoursePrinter {

    public void printListOfCourses(List<CourseDTO> courses) {
        System.out.println("List of courses: ");
        courses.forEach(System.out::println);
    }

}
