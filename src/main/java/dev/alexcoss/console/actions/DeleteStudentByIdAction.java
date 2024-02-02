package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.service.StudentService;

import java.util.Scanner;

public class DeleteStudentByIdAction extends AbstractAction {
    public DeleteStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        StudentService studentService = commandInputScanner.getStudentService();
        StudentProcessor.processStudentById(scanner, studentService::removeStudentById, "Executing command 4: Delete a student by ID: ");
    }
}
