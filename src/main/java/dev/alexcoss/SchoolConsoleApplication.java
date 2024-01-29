package dev.alexcoss;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.DatabaseConsoleManager;
import dev.alexcoss.service.GenerateStartingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SchoolConsoleApplication implements CommandLineRunner {
    private final GenerateStartingData data;
    private final DatabaseConsoleManager console;
    private final CommandInputScanner inputScanner;

    @Autowired
    public SchoolConsoleApplication(GenerateStartingData data, DatabaseConsoleManager console, CommandInputScanner inputScanner) {
        this.data = data;
        this.console = console;
        this.inputScanner = inputScanner;
    }

    public static void main(String[] args) {
		SpringApplication.run(SchoolConsoleApplication.class, args);
	}

    @Override
    public void run(String... args) {
        data.generateDataForDatabase();

        List<String> commands = console.initializeCommands();
        inputScanner.scannerRun(commands);
    }
}

