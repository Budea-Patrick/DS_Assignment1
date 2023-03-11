package com.example.Assignment1.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User user1 = new User(
                    "Patrick",
                    "20011002pat",
                    UserType.ADMIN
            );

            User user2 = new User(
                    "Dan",
                    "20011002pap",
                    UserType.CASHIER
            );

            User user3 = new User(
                    "ROCC",
                    "20011002pCOL",
                    UserType.ADMIN
            );

            repository.saveAll(List.of(user1, user2, user3));
        };

    }
}
