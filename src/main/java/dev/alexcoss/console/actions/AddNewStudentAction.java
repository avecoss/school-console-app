package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dto.StudentDTO;

import java.util.Scanner;

public class AddNewStudentAction extends AbstractAction {
    public AddNewStudentAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        scanner.nextLine();
        StudentDTO student = new StudentDTO();
        System.out.print("Enter student first name: ");
        student.setFirstName(scanner.nextLine());

        System.out.print("Enter student last name: ");
        student.setLastName(scanner.nextLine());

        commandInputScanner.getStudentService().addStudent(student);
        System.out.println("Executing command 3: Add a new student");
    }
}
