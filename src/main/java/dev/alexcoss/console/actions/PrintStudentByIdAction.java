package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.model.Student;

import java.util.Optional;
import java.util.Scanner;

public class PrintStudentByIdAction extends AbstractAction {

    public PrintStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        StudentProcessor.processStudentById(scanner, studentId -> {
                Optional<Student> optional = commandInputScanner.getStudentService().getStudentById(studentId);
                if (optional.isPresent()) {
                    System.out.println("Student: " + optional.get());
                } else {
                    System.out.println("Student not found");
                }
            },
            "Executing command 0: Print student by ID: "
        );
    }
}
