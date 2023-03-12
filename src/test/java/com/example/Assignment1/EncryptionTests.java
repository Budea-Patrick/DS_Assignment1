package com.example.Assignment1;

import com.example.Assignment1.user.User;
import com.example.Assignment1.user.UserService;
import com.example.Assignment1.user.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class EncryptionTests {

    @Test
    void testEncryption() {
        String passwordTest = "1234";
        User user = new User("Test", passwordTest, UserType.ADMIN);
        assertNotEquals(passwordTest, user.getPasswordHash());
    }
}
