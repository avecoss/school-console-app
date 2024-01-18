package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public abstract class AbstractAction implements Action {

    protected final CommandInputScanner commandInputScanner;

    protected AbstractAction(CommandInputScanner commandInputScanner) {
        this.commandInputScanner = commandInputScanner;
    }
}
