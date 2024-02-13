package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;

import java.util.Scanner;

public abstract class AbstractAction implements Action {

    protected final CommandInputScanner commandInputScanner;
    protected final Scanner scanner;

    protected AbstractAction(CommandInputScanner commandInputScanner) {
        this.commandInputScanner = commandInputScanner;
        this.scanner = new Scanner(System.in);
    }
}
