package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dao.StudentsCoursesDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentInCourseProcessor {

    public static void processStudentInCourse(Scanner scanner, CommandInputScanner inputScanner, String actionName) {
        List<Course> courses = inputScanner.getCourseDao().getAllItems();
        CoursePrinter.printListOfCourses(courses);
        scanner.nextLine();

        String inputCourseName = getCourseName(scanner);

        System.out.print("Enter the student ID: ");
        if (scanner.hasNextInt()) {
            int studentId = scanner.nextInt();

            Student student = inputScanner.getStudentDao().getStudentById(studentId);
            Course course = findCourseByName(inputCourseName, courses);

            executeAction(inputScanner, actionName, student, course, inputCourseName);
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the student ID.");
        }
        scanner.nextLine();
    }

    private static void executeAction(CommandInputScanner inputScanner, String actionName, Student student, Course course, String inputCourseName) {
        boolean isAddAction = "add".equals(actionName);

        Map<Integer, Integer> studentIdCourseId = new HashMap<>();
        studentIdCourseId.put(student.getId(), course.getId());


        StudentsCoursesDao studentsCoursesDao = inputScanner.getStudentsCoursesDao();
        if (isAddAction) {
            studentsCoursesDao.addItem(studentIdCourseId);
        } else {
            studentsCoursesDao.removeItems(studentIdCourseId);
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

    private static Course findCourseByName(String inputCourseName, List<Course> courses) {
        return courses.stream()
            .filter(c -> c.getName().equalsIgnoreCase(inputCourseName))
            .findFirst()
            .orElse(new Course());
    }
}
