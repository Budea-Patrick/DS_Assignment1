package com.example.Assignment1.utils;

import com.example.Assignment1.concert.Concert;
import com.example.Assignment1.user.User;

public class UserConcertHolder {

    private User user;
    private Concert concert;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }
}
