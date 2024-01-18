package dev.alexcoss.console.actions;

import java.util.Scanner;

@FunctionalInterface
public interface Action {
    void execute(Scanner scanner);
}
