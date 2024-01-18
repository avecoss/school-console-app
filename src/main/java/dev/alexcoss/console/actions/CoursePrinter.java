package dev.alexcoss.console.actions;

import dev.alexcoss.model.Course;

import java.util.List;

public class CoursePrinter {

    public static void printListOfCourses(List<Course> courses) {
        System.out.println("List of courses: ");
        courses.forEach(course -> {
            System.out.println(course.getName());
        });
    }
}
