package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;

import java.util.Scanner;

public class RemoveStudentFromCourseAction extends AbstractAction {
    private static final String ACTION_NAME = "remove";

    public RemoveStudentFromCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentInCourseProcessor processor = new StudentInCourseProcessor();
        processor.processStudentInCourse(scanner, commandInputScanner, ACTION_NAME);
    }
}