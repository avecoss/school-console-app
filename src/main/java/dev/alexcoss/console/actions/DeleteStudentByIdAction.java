package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dao.StudentDao;

import java.util.Scanner;

public class DeleteStudentByIdAction extends AbstractAction {
    public DeleteStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        StudentDao studentDao = commandInputScanner.getStudentDao();
        StudentProcessor.processStudentById(scanner, studentDao::removeStudentById, "Executing command 4: Delete a student by ID: ");
    }
}
