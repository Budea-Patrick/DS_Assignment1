package com.example.Assignment1.user;

import com.example.Assignment1.concert.Concert;
import com.example.Assignment1.ticket.Ticket;
import com.example.Assignment1.utils.UserConcertHolder;
import com.example.Assignment1.utils.UserTicketHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/admin/concert/add")
    public ResponseEntity<String> addConcert(@RequestBody Concert concert) {
        Pair<String, HttpStatus> pair = userService.createConcert(concert);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/admin/concert/delete")
    public ResponseEntity<String> deleteConcert(@RequestBody Concert concert) {
        Pair<String, HttpStatus> pair = userService.deleteConcert(concert);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/admin/concert/all")
    public ResponseEntity<?> getConcerts() {
        Pair<?, HttpStatus> pair = userService.findConcerts();
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/admin/concert/update")
    public ResponseEntity<?> getConcerts(@RequestBody Concert concert) {
        Pair<?, HttpStatus> pair = userService.updateConcert(concert);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("cashier/sell")
    public ResponseEntity<?> sellTicket(@RequestBody UserTicketHolder
                                                userTicketHolder) {
        User user = userTicketHolder.getUser();
        Ticket ticket = userTicketHolder.getTicket();
        Pair<?, HttpStatus> pair = userService.sellTicket(user, ticket);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("admin/export")
    public ResponseEntity<?> exportTickets(@RequestBody UserConcertHolder
                                                   userConcertHolder) throws IOException {
        User user = userConcertHolder.getUser();
        Concert concert = userConcertHolder.getConcert();
        Pair<?, HttpStatus> pair = userService.exportTickets(user, concert);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("cashier/list")
    public ResponseEntity<?> listTickets(@RequestBody UserConcertHolder
                                                 userConcertHolder) throws IOException {
        User user = userConcertHolder.getUser();
        Concert concert = userConcertHolder.getConcert();
        Pair<?, HttpStatus> pair = userService.listTicketsForConcert(user, concert);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }
}
