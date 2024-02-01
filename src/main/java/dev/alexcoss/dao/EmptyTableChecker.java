package dev.alexcoss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmptyTableChecker implements TableValidator {
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM %s";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmptyTableChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isTableEmpty(String tableName) {
        return jdbcTemplate.queryForObject(String.format(COUNT_SQL, tableName), Integer.class) == 0;
    }
}
