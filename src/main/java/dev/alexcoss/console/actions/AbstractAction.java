package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;

public abstract class AbstractAction implements Action {

    protected final CommandInputScanner commandInputScanner;

    protected AbstractAction(CommandInputScanner commandInputScanner) {
        this.commandInputScanner = commandInputScanner;
    }
}
