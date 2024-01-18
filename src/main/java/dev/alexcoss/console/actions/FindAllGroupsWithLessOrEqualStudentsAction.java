package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.model.Group;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FindAllGroupsWithLessOrEqualStudentsAction extends AbstractAction {
    public FindAllGroupsWithLessOrEqualStudentsAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        Map<Group, Integer> allGroupsWithStudents = commandInputScanner.getGroupDao().getAllGroupsWithStudents();

        List<Map.Entry<Group, Integer>> minEntries = allGroupsWithStudents.entrySet().stream()
            .collect(Collectors.groupingBy(Map.Entry::getValue))
            .entrySet().stream()
            .min(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElse(Collections.emptyList());

        System.out.println("Executing command 1: Find all groups with less or equal students number");

        minEntries.forEach(entry -> {
            System.out.println("Group: " + entry.getKey());
            System.out.println("Number of students: " + entry.getValue());
        });
    }
}
