package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dto.StudentDTO;

import java.util.Optional;

public class PrintStudentByIdAction extends AbstractAction {

    public PrintStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentProcessor processor = new StudentProcessor();
        processor.processStudentById(scanner, studentId -> {
                Optional<StudentDTO> optional = commandInputScanner.getStudentService().getStudentById(studentId);
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
