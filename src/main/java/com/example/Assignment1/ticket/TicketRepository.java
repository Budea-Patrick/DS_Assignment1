package com.example.Assignment1.ticket;

import com.example.Assignment1.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByConcert(Concert concert);
}
