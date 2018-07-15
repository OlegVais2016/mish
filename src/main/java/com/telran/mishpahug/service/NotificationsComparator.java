package com.telran.mishpahug.service;

import com.telran.mishpahug.model.Notification;

import java.util.Comparator;

public class NotificationsComparator implements Comparator<Notification> {
    @Override
    public int compare(Notification o1, Notification o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
