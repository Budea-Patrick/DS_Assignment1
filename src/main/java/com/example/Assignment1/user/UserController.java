package com.example.Assignment1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getUsers(@RequestBody User user) {
        User foundUser = userService.findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if(foundUser.getLoggedIn().equals(Boolean.FALSE)) {
            return new ResponseEntity<>("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if(!foundUser.getUserType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("User does not have permissions", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
    }

    @GetMapping("/admin/admins")
    public ResponseEntity<?> getAdmins(@RequestBody User user) {
        User foundUser = userService.findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if(foundUser.getLoggedIn().equals(Boolean.FALSE)) {
            return new ResponseEntity<>("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if(!foundUser.getUserType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("User does not have permissions", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.findAdmins(), HttpStatus.OK);
    }

    @GetMapping("/admin/cashiers")
    public ResponseEntity<?> getCashiers(@RequestBody User user) {
        User foundUser = userService.findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if(foundUser.getLoggedIn().equals(Boolean.FALSE)) {
            return new ResponseEntity<>("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if(!foundUser.getUserType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("User does not have permissions", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.findCashiers(), HttpStatus.OK);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateCashier(@RequestBody User user) {
        Pair<String, HttpStatus> pair = userService.updateUser(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // TODO: validate
        Pair<String, HttpStatus> pair = userService.addNewUser(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Pair<String, HttpStatus> pair = userService.login(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody User user) {
        Pair<String, HttpStatus> pair = userService.logout(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

}
