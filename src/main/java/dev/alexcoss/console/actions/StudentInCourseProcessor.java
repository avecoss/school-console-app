package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import dev.alexcoss.service.StudentCourseService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentInCourseProcessor {

    public static void processStudentInCourse(Scanner scanner, CommandInputScanner inputScanner, String actionName) {
        List<CourseDTO> courses = inputScanner.getCourseService().getCourses();
        CoursePrinter.printListOfCourses(courses);
        scanner.nextLine();

        String inputCourseName = getCourseName(scanner);

        System.out.print("Enter the student ID: ");
        if (scanner.hasNextInt()) {
            int studentId = scanner.nextInt();

            Optional<StudentDTO> optionalStudent = inputScanner.getStudentService().getStudentById(studentId);
            Optional<CourseDTO> optionalCourse = findCourseByName(inputCourseName, courses);

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

    private static void executeAction(CommandInputScanner inputScanner, String actionName, StudentDTO student, CourseDTO course, String inputCourseName) {
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

    private static Optional<CourseDTO> findCourseByName(String inputCourseName, List<CourseDTO> courses) {
        return courses.stream()
            .filter(c -> c.getName().equalsIgnoreCase(inputCourseName))
            .findFirst();
    }
}
