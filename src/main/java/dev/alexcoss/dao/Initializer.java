package dev.alexcoss.dao;

@FunctionalInterface
public interface Initializer {
    void initializeDatabase();
}
