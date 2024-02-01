package dev.alexcoss.dao;

@FunctionalInterface
public interface TableValidator {
    boolean isTableEmpty(String tableName);
}
