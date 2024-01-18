package dev.alexcoss.console.actions;

import java.util.Scanner;
import java.util.function.Consumer;

public class StudentProcessor {

    public static void processStudentById(Scanner scanner, Consumer<Integer> action, String actionName) {
        System.out.print("Enter the student ID: ");
        if (scanner.hasNextInt()) {
            int studentId = scanner.nextInt();
            scanner.nextLine();
            action.accept(studentId);
            System.out.println(actionName + studentId);
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the student ID.");
            scanner.nextLine();
        }
    }
}
