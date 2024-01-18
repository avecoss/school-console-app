package dev.alexcoss.console;

import dev.alexcoss.util.FileReader;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConsoleManager {
    private static final String COMMANDS_PATH = "src/main/resources/data/commands.txt";
    private static final String TITLE = "Interface to manage database";
    private static final String EXIT = "enter 'exit' to close the application\n";
    private static final int REPEAT_COUNT = 3;

    private final StringBuilder welcomeText;
    private final List<String> commands;

    public DatabaseConsoleManager() {
        this.welcomeText = new StringBuilder();
        this.commands = readList();
    }

    public List<String> initializeCommands() {
        generateWelcomeText();
        System.out.println(welcomeText);
        return commands;
    }

    private void generateWelcomeText() {
        String title = generateTitle();
        String frame = String.format("%s\n", "#".repeat(calculateHashRepeats(title)));

        welcomeText.append(frame);
        welcomeText.append(title);
        welcomeText.append(frame);
        welcomeText.append("\n");

        for (int i = 0; i < commands.size(); i++) {
            welcomeText.append("enter ").append(i).append(" ").append(commands.get(i)).append("\n");
        }

        welcomeText.append(EXIT);
    }

    private String generateTitle() {
        return "#".repeat(REPEAT_COUNT) +
            " ".repeat(REPEAT_COUNT) +
            TITLE +
            " ".repeat(REPEAT_COUNT) +
            "#".repeat(REPEAT_COUNT) +
            "\n";
    }

    private int calculateHashRepeats(String string) {
        return string.length() - 1;
    }

    private List<String> readList() {
        FileReader reader = new FileReader();
        return reader.fileRead(COMMANDS_PATH, bufferedReader -> {
            List<String> list = new ArrayList<>();
            bufferedReader.lines().forEach(list::add);
            return list;
        });
    }
}
