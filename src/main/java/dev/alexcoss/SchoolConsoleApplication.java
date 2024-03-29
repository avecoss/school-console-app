package dev.alexcoss;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.DatabaseConsoleManager;
import dev.alexcoss.service.generator.GenerateStartingData;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = "dev.alexcoss")
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

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}