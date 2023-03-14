package com.example.Assignment1.user;

import com.example.Assignment1.concert.Concert;
import com.example.Assignment1.concert.ConcertRepository;
import com.example.Assignment1.ticket.Ticket;
import com.example.Assignment1.ticket.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final ConcertRepository concertRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public UserService(UserRepository userRepository, ConcertRepository concertRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.concertRepository = concertRepository;
        this.ticketRepository = ticketRepository;
    }

    public Pair<?, HttpStatus> findUsers(User user) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if (foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if (!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(userRepository.findAll(), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAdmins(User user) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if (foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if (!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(userRepository.findUserByUserType(UserType.ADMIN), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findCashiers(User user) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if (foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if (!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(userRepository.findUserByUserType(UserType.CASHIER), HttpStatus.OK);
    }

    public Pair<String, HttpStatus> addNewUser(User user) {
        if (userRepository.findUserByUserName(user.getUserName()) != null) {
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
        if (foundUser == null) {
            return Pair.of("Username or password incorrect.", HttpStatus.BAD_REQUEST);
        }

        if (foundUser.getLoggedIn().equals(Boolean.TRUE)) {
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
        if (foundUser == null) {
            return Pair.of("Username incorrect.", HttpStatus.BAD_REQUEST);
        }

        if (foundUser.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in.", HttpStatus.BAD_REQUEST);
        }

        foundUser.setLoggedIn(Boolean.FALSE);
        userRepository.save(foundUser);
        return Pair.of("You have successfully logged out", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateUser(User user) {
        String userName = user.getUserName();

        User foundUser = userRepository.findUserByUserName(userName);
        if (foundUser == null) {
            return Pair.of("Username incorrect.", HttpStatus.BAD_REQUEST);
        }

        if (foundUser.getUserType().equals(UserType.ADMIN)) {
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
        if (foundUser == null) {
            return Pair.of("User cannot be found.", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(foundUser);
        return Pair.of("User has been deleted", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> createConcert(Concert concert) {
        if (concert.getTickets() > 20000) {
            return Pair.of("Maximum 20000 tickets allowed", HttpStatus.BAD_REQUEST);
        }

        concertRepository.save(concert);
        return Pair.of("Concert has been created", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> deleteConcert(Concert concert) {
        Concert foundConcert = concertRepository.findConcertById(concert.getId());
        if (foundConcert == null) {
            return Pair.of("Concert cannot be found.", HttpStatus.BAD_REQUEST);
        }

        concertRepository.delete(concert);
        return Pair.of("Concert has been deleted", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findConcerts() {
        return Pair.of(concertRepository.findAll(), HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateConcert(Concert concert) {
        Concert foundConcert = concertRepository.findConcertById(concert.getId());
        if (foundConcert == null) {
            return Pair.of("Concert cannot be found", HttpStatus.OK);
        }

        foundConcert = concert;
        concertRepository.save(foundConcert);
        return Pair.of("Concert has been updated", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> sellTicket(User user, Ticket ticket) {
        String userName = user.getUserName();
        String password = user.getPasswordHash();

        User foundUser = findUserByCredentials(userName, password);
        if (foundUser == null) {
            return Pair.of("Username or password incorrect.", HttpStatus.BAD_REQUEST);
        }

        if (foundUser.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("Admin cannot do this operation.", HttpStatus.BAD_REQUEST);
        }

        if (foundUser.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in.", HttpStatus.BAD_REQUEST);
        }

        Concert foundConcert = concertRepository.findConcertById(ticket.getConcert().getId());
        List<Ticket> boughtTickets = ticketRepository.findByConcert(foundConcert);

        Integer alreadySoldTickets = 0;
        if (!boughtTickets.isEmpty()) {
            alreadySoldTickets = boughtTickets.stream()
                    .map(Ticket::getTicketsBought)
                    .reduce(Integer::sum).get();
        }

        if (alreadySoldTickets + ticket.getTicketsBought() > foundConcert.getTickets()) {
            return Pair.of("Cannot buy that many tickets for this show", HttpStatus.BAD_REQUEST);
        }


        ticket.setUser(foundUser);
        ticket.setConcert(concertRepository.findConcertById(ticket.getConcert().getId()));

        ticketRepository.save(ticket);
        return Pair.of("Ticket has been added.", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> exportTickets(User user, Concert concert) throws IOException {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if (foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if (!foundUsers.getUserType().equals(UserType.ADMIN)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        Concert foundConcert = concertRepository.findConcertById(concert.getId());
        if (foundConcert == null) {
            return Pair.of("Cannot find concert", HttpStatus.BAD_REQUEST);
        }

        List<Ticket> foundTickets = ticketRepository.findByConcert(foundConcert);
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get("tickets.json").toFile(), foundTickets);

        return Pair.of("Tickets exported", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> listTicketsForConcert(User user, Concert concert) {
        User foundUsers = findUserByCredentials(user.getUserName(), user.getPasswordHash());
        if (foundUsers.getLoggedIn().equals(Boolean.FALSE)) {
            return Pair.of("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        if (!foundUsers.getUserType().equals(UserType.CASHIER)) {
            return Pair.of("User does not have permissions", HttpStatus.BAD_REQUEST);
        }

        Concert foundConcert = concertRepository.findConcertById(concert.getId());
        if (foundConcert == null) {
            return Pair.of("Cannot find concert", HttpStatus.BAD_REQUEST);
        }

        List<Ticket> tickets = ticketRepository.findByConcert(concert);
        return Pair.of(tickets, HttpStatus.OK);

    }

}
