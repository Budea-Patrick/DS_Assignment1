package com.example.Assignment1.utils;

import com.example.Assignment1.ticket.Ticket;
import com.example.Assignment1.user.User;

public class UserTicketHolder {

    private User user;
    private Ticket ticket;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
