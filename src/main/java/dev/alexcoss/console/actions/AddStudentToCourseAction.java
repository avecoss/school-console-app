package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;

public class AddStudentToCourseAction extends AbstractAction {
    private static final String ACTION_NAME = "add";

    public AddStudentToCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentInCourseProcessor processor = new StudentInCourseProcessor();
        processor.processStudentInCourse(scanner, commandInputScanner, ACTION_NAME);
    }
}
