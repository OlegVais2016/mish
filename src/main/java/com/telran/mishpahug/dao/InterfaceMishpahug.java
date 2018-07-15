package com.telran.mishpahug.dao;

import com.telran.mishpahug.api.eventAPI.EventDTO;

import com.telran.mishpahug.api.userAPI.ProfileDTO;
import com.telran.mishpahug.api.userAPI.StaticFieldsDTO;
import com.telran.mishpahug.model.Event;
import com.telran.mishpahug.model.Notification;
import com.telran.mishpahug.model.User;

import java.util.List;

public interface InterfaceMishpahug {

    User registrationUser(String email,String password);
    User loginUser(String email,String password);
    String addEvent(EventDTO eventDTO,String email,String password,Event event);
    User updateProfile(ProfileDTO profileDTO,String userAuth);
    void subscribeToEvent(List<Event> events, Event event);
    void unSubscribeFromEvent(List<Event> events, Event event);
    void voteForeEvent(User owner,int count,double current);
    void inviteToEvent(User newParticipant,Event event);
    Event changeEventStatus(Event event);
    void changeStatusToDone(Event event);
    void notificationIsRead(User beneficiary,Notification notification);
    void removeEvent(Event event);
    boolean createNotification(long eventId,int userId,String userAuth);
    List<Event> getAllEvents();
    List<Event> getAllMyEventsList(String userAuth);
    User getUserByAuth(String userAuth);
    Event getEventById(long eventId);
    User getUserById(int userId);
    Notification getNotificationById(int notificationId);
}




