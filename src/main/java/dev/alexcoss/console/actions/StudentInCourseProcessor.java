package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;
import dev.alexcoss.service.StudentCourseService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentInCourseProcessor {

    public static void processStudentInCourse(Scanner scanner, CommandInputScanner inputScanner, String actionName) {
        List<Course> courses = inputScanner.getCourseService().getCourses();
        CoursePrinter.printListOfCourses(courses);
        scanner.nextLine();

        String inputCourseName = getCourseName(scanner);

        System.out.print("Enter the student ID: ");
        if (scanner.hasNextInt()) {
            int studentId = scanner.nextInt();

            Optional<Student> optionalStudent = inputScanner.getStudentService().getStudentById(studentId);
            Optional<Course> optionalCourse = findCourseByName(inputCourseName, courses);

            if (optionalStudent.isEmpty()) {
                System.out.println("Student not found. Please enter a valid student ID.");
                return;
            }

            if (optionalCourse.isEmpty()) {
                System.out.println("Course not found. Please enter a valid course name.");
                return;
            }

            executeAction(inputScanner, actionName, optionalStudent.get(), optionalCourse.get(), inputCourseName);
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the student ID.");
        }
        scanner.nextLine();
    }

    private static void executeAction(CommandInputScanner inputScanner, String actionName, Student student, Course course, String inputCourseName) {
        boolean isAddAction = "add".equals(actionName);

        StudentCourseService studentCourseService = inputScanner.getStudentCourseService();
        if (isAddAction) {
            studentCourseService.addStudentToCourse(student.getId(), course.getId());
        } else {
            studentCourseService.removeStudentFromCourse(student.getId(), course.getId());
        }

        System.out.printf("Executing command %s: %s the student %s %s %s the course %s\n",
            (isAddAction) ? "5" : "6",
            (isAddAction) ? "Add" : "Remove",
            student.getFirstName(),
            student.getLastName(),
            (isAddAction) ? "to" : "from",
            inputCourseName);
    }

    private static String getCourseName(Scanner scanner) {
        System.out.print("Enter the course name: ");
        return scanner.nextLine();
    }

    private static Optional<Course> findCourseByName(String inputCourseName, List<Course> courses) {
        return courses.stream()
            .filter(c -> c.getName().equalsIgnoreCase(inputCourseName))
            .findFirst();
    }
}
