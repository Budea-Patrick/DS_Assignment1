package com.example.Assignment1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Pair<?, HttpStatus> findUsers(User user) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if(foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if(!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(userRepository.findAll(), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAdmins(User user) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if(foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if(!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(userRepository.findUserByUserType(UserType.ADMIN), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findCashiers(User user) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if(foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if(!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(userRepository.findUserByUserType(UserType.CASHIER), HttpStatus.OK);
    }

    public Pair<String, HttpStatus> addNewUser(User user) {
        if(userRepository.findUserByUserName(user.getUserName()) != null) {
            return Pair.of("Username already exists.", HttpStatus.BAD_REQUEST);
        }
        user.setPasswordHash(Base64.getEncoder().encodeToString(user.getPasswordHash().getBytes()));
        user.setLoggedIn(Boolean.FALSE);
        userRepository.save(user);
        return Pair.of("User has been added", HttpStatus.OK);
    }

    public User findUserByCredentials(String userName, String password) {
        Optional<User> foundUser = userRepository.findUserByUserNameAndPasswordHash(
                userName, Base64.getEncoder().encodeToString(password.getBytes()))
                .stream().findFirst();
        return foundUser.orElse(null);
    }

    public Pair<String, HttpStatus> login(User user) {
        String userName = user.getUserName();
        String password = user.getPasswordHash();

        User foundUser = findUserByCredentials(userName, password);
        if(foundUser == null) {
            return Pair.of("Username or password incorrect.", HttpStatus.BAD_REQUEST);
        }

        if(foundUser.getLoggedIn().equals(Boolean.TRUE)) {
            return Pair.of("User already logged in.", HttpStatus.BAD_REQUEST);
        }

        foundUser.setLoggedIn(Boolean.TRUE);
        userRepository.save(foundUser);
        return Pair.of("You have successfully logged in", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> logout(User user) {
        String userName = user.getUserName();
        String password = user.getPasswordHash();

        User foundUser = userRepository.findUserByUserName(userName);
        if(foundUser == null) {
            return Pair.of("Username incorrect.", HttpStatus.BAD_REQUEST);
        }

        if(foundUser.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in.", HttpStatus.BAD_REQUEST);
        }

        foundUser.setLoggedIn(Boolean.FALSE);
        userRepository.save(foundUser);
        return Pair.of("You have successfully logged out", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateUser(User user) {
        String userName = user.getUserName();

        User foundUser = userRepository.findUserByUserName(userName);
        if(foundUser == null) {
            return Pair.of("Username incorrect.", HttpStatus.BAD_REQUEST);
        }

        if(foundUser.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("Cannot update admin.", HttpStatus.BAD_REQUEST);
        }

        foundUser.setUserName(user.getNewName());
        foundUser.setPasswordHash(Base64.getEncoder().encodeToString(user.getNewPassword().getBytes()));
        userRepository.save(foundUser);
        return Pair.of("User has been updated", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> deleteUser(User user) {
        String userName = user.getUserName();

        User foundUser = userRepository.findUserByUserName(userName);
        if(foundUser == null) {
            return Pair.of("User cannot be found.", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(foundUser);
        return Pair.of("User has been deleted", HttpStatus.OK);
    }

}
