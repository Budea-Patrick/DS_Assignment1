package com.example.Assignment1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private DataSource dataSource;
    @Test
    void testDatabaseConnection() throws SQLException {
        assertFalse(dataSource.getConnection().isClosed(), "Database connection could not be established");
    }
}
