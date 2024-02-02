package dev.alexcoss;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.DatabaseConsoleManager;
import dev.alexcoss.service.generator.GenerateStartingData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
public class SchoolConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolConsoleApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner commandLineRunner(GenerateStartingData data, DatabaseConsoleManager console, CommandInputScanner inputScanner) {
        return args -> {
            data.generateDataForDatabase();
            List<String> commands = console.initializeCommands();
            inputScanner.scannerRun(commands);
        };
    }
}

