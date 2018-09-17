package com.example.josepablo.myapplication.model;

import java.util.Date;

public class Event {

    public String band;
    public int eventID;
    public String place;
    public Date date;
    public String details;

    public Event(String b, int e, String p, Date d, String dt) {
        band = b;
        eventID = e;
        place = p;
        date = d;
        details = dt;
    }


}

