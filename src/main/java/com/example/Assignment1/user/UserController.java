package com.example.Assignment1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Pair<?, HttpStatus> pair = userService.findUsers(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/admin/admins")
    public ResponseEntity<?> getAdmins(@RequestBody User user) {
        Pair<?, HttpStatus> pair = userService.findAdmins(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/admin/cashiers")
    public ResponseEntity<?> getCashiers(@RequestBody User user) {
        Pair<?, HttpStatus> pair = userService.findCashiers(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateCashier(@RequestBody User user) {
        Pair<String, HttpStatus> pair = userService.updateUser(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Pair<String, HttpStatus> pair = userService.addNewUser(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        Pair<String, HttpStatus> pair = userService.deleteUser(user);
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
