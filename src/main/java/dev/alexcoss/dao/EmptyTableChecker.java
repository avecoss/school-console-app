package dev.alexcoss.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmptyTableChecker implements TableValidator {
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM %s";

    private final JdbcTemplate jdbcTemplate;

    public EmptyTableChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isTableEmpty(String tableName) {
        return jdbcTemplate.queryForObject(String.format(COUNT_SQL, tableName), Integer.class) == 0;
    }
}
