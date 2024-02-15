package dev.alexcoss.console;

import dev.alexcoss.console.actions.*;
import dev.alexcoss.service.CourseService;
import dev.alexcoss.service.GroupService;
import dev.alexcoss.service.StudentCourseService;
import dev.alexcoss.service.StudentService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CommandInputScanner {
    private static final String EXIT_COMMAND = "exit";

    @Getter
    private final StudentService studentService;
    @Getter
    private final GroupService groupService;
    @Getter
    private final CourseService courseService;
    @Getter
    private final StudentCourseService studentCourseService;
    private final Map<Integer, Action> actions = new HashMap<>();

    public CommandInputScanner(StudentService studentService, GroupService groupService, CourseService courseService, StudentCourseService studentCourseService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.studentCourseService = studentCourseService;

        populateActionsMap();
    }

    public void scannerRun(List<String> commands) {
        int commandsCount = commands.size() - 1;
        boolean isRunning = true;

        try (Scanner scanner = new Scanner(System.in)) {

            while (isRunning) {
                System.out.println("Enter a command:");
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    processIntegerInput(commandsCount, number);
                } else {
                    String line = scanner.nextLine();
                    if (EXIT_COMMAND.equals(line)) {
                        isRunning = false;
                    } else {
                        System.out.println("Invalid input. Please enter a valid command or 'exit' to close the application.");
                    }
                }
            }
        }
    }

    private void processIntegerInput(int commandsCount, int number) {
        if (number >= 0 && number <= commandsCount) {
            executeCommand(number);
        } else {
            System.out.println("Invalid command number. Please enter a valid command.");
        }
    }

    private void executeCommand(int commandNumber) {
        Action action = actions.get(commandNumber);
        if (action != null) {
            action.execute();
        } else {
            System.out.println("Invalid command number. Please enter a valid command.");
        }
    }

    private void populateActionsMap() {
        actions.put(0, new PrintStudentByIdAction(this));
        actions.put(1, new FindAllGroupsWithLessOrEqualStudentsAction(this));
        actions.put(2, new FindAllStudentsRelatedToCourseAction(this));
        actions.put(3, new AddNewStudentAction(this));
        actions.put(4, new DeleteStudentByIdAction(this));
        actions.put(5, new AddStudentToCourseAction(this));
        actions.put(6, new RemoveStudentFromCourseAction(this));
    }
}
