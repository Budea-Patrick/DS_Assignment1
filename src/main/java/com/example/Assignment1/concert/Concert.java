package com.example.Assignment1.concert;

import com.example.Assignment1.artist.Artist;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "concerts")
public class Concert {
    @Id
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Artist> artists;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer tickets;

//    public Concert(Long id, Set<Artist> artists, String genre, LocalDateTime date, String title, Integer tickets) {
//        this.id = id;
//        this.artists = artists;
//        this.genre = genre;
//        this.date = date;
//        this.title = title;
//        this.tickets = tickets;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }
}
