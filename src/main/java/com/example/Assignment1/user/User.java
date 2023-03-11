package com.example.Assignment1.user;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User() {
    }

    public User(String userName, String passwordHash, UserType userType) {
        this.userName = userName;
        this.passwordHash = Base64.getEncoder().encodeToString(passwordHash.getBytes());
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isMatchingPassword(String passwordHash) {
        String candidateHash = Base64.getEncoder().encodeToString(passwordHash.getBytes());
        return candidateHash.equals(passwordHash);
    }
}
