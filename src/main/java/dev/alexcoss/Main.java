package dev.alexcoss;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.DatabaseConsoleManager;
import dev.alexcoss.dao.*;
import dev.alexcoss.service.GenerateStartingData;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new PostgreSqlConnectionFactory();

        Remover removerManager = new DatabaseRemover(connectionFactory);
        removerManager.removeDatabase();

        Initializer initializerManager = new DatabaseInitializer(connectionFactory);
        initializerManager.initializeDatabase();

        GenerateStartingData data = new GenerateStartingData(connectionFactory);
        data.generateDataForDatabase();

        DatabaseConsoleManager console = new DatabaseConsoleManager();
        List<String> commands = console.initializeCommands();

        CommandInputScanner inputScanner = new CommandInputScanner(data.getStudentDao(), data.getGroupDao(), data.getCourseDao(), data.getStudentsCoursesDao());
        inputScanner.scannerRun(commands);
    }
}