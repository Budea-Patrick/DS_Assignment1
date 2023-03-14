package com.example.Assignment1.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

    public Concert findConcertById(Long id);

}
