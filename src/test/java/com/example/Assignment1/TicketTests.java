package com.example.Assignment1;

import com.example.Assignment1.artist.Artist;
import com.example.Assignment1.concert.Concert;
import com.example.Assignment1.ticket.Ticket;
import com.example.Assignment1.user.User;
import com.example.Assignment1.user.UserService;
import com.example.Assignment1.user.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class TicketTests {

    private UserService userService;
    @Test
    void testTicketLimit() {
//        User user = new User("TEST", "1234", UserType.CASHIER, Boolean.TRUE);
//        Artist artist = new Artist(1L, "TEST_ARTIST");
//        Concert concert = new Concert(1L,Set.of(artist),"TEST_GENRE", LocalDateTime.now(),"TEST_TITLE", 50);
//        Ticket ticket = new Ticket(1L,20,concert,user,20);
//        HttpStatus status = userService.sellTicket(user,ticket).getSecond();
//        assertNotEquals(status, HttpStatus.BAD_REQUEST);
    }
}
