package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.service.StudentService;

public class DeleteStudentByIdAction extends AbstractAction {
    public DeleteStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentProcessor processor = new StudentProcessor();
        StudentService studentService = commandInputScanner.getStudentService();

        processor.processStudentById(scanner, studentService::removeStudentById, "Executing command 4: Delete a student by ID: ");
    }
}
