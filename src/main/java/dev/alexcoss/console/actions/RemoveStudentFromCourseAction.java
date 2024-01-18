package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dao.StudentsCoursesDao;

import java.util.Scanner;

public class RemoveStudentFromCourseAction extends AbstractAction {
    private static final String ACTION_NAME = "remove";

    public RemoveStudentFromCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        StudentInCourseProcessor.processStudentInCourse(scanner, commandInputScanner, ACTION_NAME);
    }
}
