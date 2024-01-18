package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.model.Student;

import java.util.Scanner;

public class AddNewStudentAction extends AbstractAction {
    public AddNewStudentAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        scanner.nextLine();
        Student student = new Student();
        System.out.print("Enter student first name: ");
        student.setFirstName(scanner.nextLine());

        System.out.print("Enter student last name: ");
        student.setLastName(scanner.nextLine());

        commandInputScanner.getStudentDao().addItem(student);
        System.out.println("Executing command 3: Add a new student");
    }
}
