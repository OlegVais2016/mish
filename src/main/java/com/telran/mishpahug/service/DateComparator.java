package com.telran.mishpahug.service;

import com.telran.mishpahug.model.Event;

import java.util.Comparator;

public class DateComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}


