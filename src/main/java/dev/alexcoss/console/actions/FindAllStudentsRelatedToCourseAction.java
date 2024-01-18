package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.util.List;
import java.util.Scanner;

public class FindAllStudentsRelatedToCourseAction extends AbstractAction {
    public FindAllStudentsRelatedToCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        List<Course> courses = commandInputScanner.getCourseDao().getAllItems();
        CoursePrinter.printListOfCourses(courses);

        scanner.nextLine();

        System.out.print("Enter the course name: ");
        String courseName = scanner.nextLine();

        System.out.println("Executing command 2: Find all students related to the course with the given name " + courseName);

        List<Student> studentsByCourse = commandInputScanner.getStudentDao().getStudentsByCourse(courseName);
        if (studentsByCourse.isEmpty()) {
            System.out.println("No students found for this course title " + courseName);
        } else {
            studentsByCourse.forEach(System.out::println);

        }
    }
}
