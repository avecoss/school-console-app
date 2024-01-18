package dev.alexcoss.console;

import dev.alexcoss.console.actions.*;
import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dao.StudentsCoursesDao;

import java.util.*;

public class CommandInputScanner {
    private static final String EXIT_COMMAND = "exit";

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    private final StudentsCoursesDao studentsCoursesDao;
    private final Map<Integer, Action> actions = new HashMap<>();

    public CommandInputScanner(StudentDao studentDao, GroupDao groupDao, CourseDao courseDao, StudentsCoursesDao studentsCoursesDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.studentsCoursesDao = studentsCoursesDao;

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
                    processIntegerInput(commandsCount, number, scanner);
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

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public CourseDao getCourseDao() {
        return courseDao;
    }

    public StudentsCoursesDao getStudentsCoursesDao() {
        return studentsCoursesDao;
    }

    private void processIntegerInput(int commandsCount, int number, Scanner scanner) {
        if (number >= 0 && number <= commandsCount) {
            executeCommand(number, scanner);
        } else {
            System.out.println("Invalid command number. Please enter a valid command.");
        }
    }

    private void executeCommand(int commandNumber, Scanner scanner) {
        Action action = actions.get(commandNumber);
        if (action != null) {
            action.execute(scanner);
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
