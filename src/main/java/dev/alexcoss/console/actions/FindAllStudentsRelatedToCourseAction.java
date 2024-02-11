package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.dto.StudentDTO;

import java.util.List;

public class FindAllStudentsRelatedToCourseAction extends AbstractAction {
    public FindAllStudentsRelatedToCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        CoursePrinter coursePrinter = new CoursePrinter();
        List<CourseDTO> courses = commandInputScanner.getCourseService().getCourses();
        coursePrinter.printListOfCourses(courses);

        System.out.print("Enter the course name: ");
        String courseName = scanner.nextLine();

        System.out.println("Executing command 2: Find all students related to the course with the given name " + courseName);

        List<StudentDTO> studentsByCourse = commandInputScanner.getStudentService().getStudentsByCourse(courseName);
        if (studentsByCourse.isEmpty()) {
            System.out.println("No students found for this course title " + courseName);
        } else {
            studentsByCourse.forEach(System.out::println);
        }
    }
}
