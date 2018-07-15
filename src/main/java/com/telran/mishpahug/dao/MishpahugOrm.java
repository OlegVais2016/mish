package com.telran.mishpahug.dao;

import com.telran.mishpahug.api.eventAPI.AddressDTO;
import com.telran.mishpahug.api.eventAPI.EventDTO;

import com.telran.mishpahug.api.eventAPI.SubEventDTO;
import com.telran.mishpahug.api.userAPI.OwnerDTO;
import com.telran.mishpahug.api.userAPI.ProfileDTO;
import com.telran.mishpahug.api.userAPI.StaticFieldsDTO;
import com.telran.mishpahug.model.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MishpahugOrm implements InterfaceMishpahug {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JavaMailSender emailSender;

    @Override
    @Transactional
    public User registrationUser(String email,String password) {
        User user = new User(email,password);
        em.persist(user);
        return user;
    }
    /*@Override
    public String loginUser(String email,String password) {
        User user = em.find(User.class, email);
        if (user == null)
            throw new IllegalArgumentException(email);
        String token = null;
        if(password.equals(user.getPassword())){
            String res = email+":"+password;
            token = Base64.getEncoder().encodeToString(res.getBytes());
        }

        return token;
    }*/
    @Override
    @Transactional
    public User loginUser(String email,String password) {

        TypedQuery<User> query =
                em.createQuery("select u from User u where email=?1",
                        User.class);
        query.setParameter(1,email);
        User user = query.getSingleResult();
        if (user == null)
            throw new IllegalArgumentException(email);
        if(password.equals(user.getPassword())){
            return user;
        }
        throw new NoSuchElementException();
    }
    @Override
    @Transactional
    public String addEvent
            (EventDTO eventDTO,String email,String password,Event event) {

        TypedQuery<User> query =
                em.createQuery("select u from User u where email=?1",
                        User.class);
        query.setParameter(1,email);
        User user = query.getSingleResult();

        if (user == null)
            throw new IllegalArgumentException(email);
        String message;
        if(password.equals(user.getPassword())){
            message = "Event was created!";
            event.setOwner(user);
            em.persist(event);
        }
        else {
            message = "Error authorization";
        }
        return message;
    }
    @Override
    @Transactional
    public User updateProfile(ProfileDTO profileDTO,String userAuth) {
        User user = getUserByAuth(userAuth);
        if (user == null) {
             throw new IllegalArgumentException(userAuth);
        }
        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setDateOfBirth(profileDTO.getDateOfBirth());
        user.setGender(profileDTO.getGender());
        user.setMaritalStatus(profileDTO.getMaritalStatus());
        user.setConfession(profileDTO.getConfession());
        user.setPictureLink(profileDTO.getPictureLink());
        user.setPhoneNumber(profileDTO.getPhoneNumber());
        user.setFoodPreferences(profileDTO.getFoodPreferences());
        user.setLanguage(profileDTO.getLanguage());
        user.setDescription(profileDTO.getDescription());
        user.setRate(profileDTO.getRate());
        user.setNumberOfVoters(profileDTO.getNumberOfVoters());
        return user;
    }

    @Override
    @Transactional
    public void subscribeToEvent(List<Event> events, Event event){
        events.add(event);
    }

    @Override
    @Transactional
    public void unSubscribeFromEvent(List<Event> events, Event event) {
        events.remove(event);
    }

    @Override
    @Transactional
    public void voteForeEvent(User owner, int count, double current) {
        owner.setNumberOfVoters(count);
        owner.setRate(current);
    }

    @Override
    @Transactional
    public void inviteToEvent(User newParticipant, Event event) {
        newParticipant.getInvitedEventsId().add(event.getEventId());
        newParticipant.setInvited(true);
    }

    @Override
    @Transactional
    public Event changeEventStatus(Event event) {
        event.setStatus("pending");
        return event;
    }

    @Override
    @Transactional
    public void changeStatusToDone(Event event) {
        event.setStatus("done");
    }

    @Override
    @Transactional
    public void notificationIsRead(User beneficiary, Notification notification) {
        if(notification.getBeneficiary().equals(beneficiary)) {
            notification.setRead(true);
            notification.setMessage("Notification is updated!");
        }
    }

    @Override
    @Transactional
    public void removeEvent(Event event) {
        em.remove(event);
    }

    @Override
    @Transactional
    public boolean createNotification(long eventId, int userId, String userAuth) {
        User owner = getUserByAuth(userAuth);
        if (owner == null) {
            throw new IllegalArgumentException(userAuth);
        }
        Event event = em.find(Event.class,eventId);
        Notification notification = new Notification();
        notification.setTitle(event.getTitle());
        notification.setMessage("You are invited");
        notification.setDate(event.getDate());
        notification.setType("what's this?");
        notification.setRead(false);
        notification.setOwner(owner);
        User beneficiary = em.find(User.class,userId);
        notification.setBeneficiary(beneficiary);
        beneficiary.getNotifications().add(notification);
        em.persist(notification);
        return true;
    }

    @Override
    public List<Event> getAllEvents() {
        TypedQuery<Event> query = em.createQuery
                ("Select e from Event e",Event.class);
        return query.getResultList();
    }

    @Override
    public List<Event> getAllMyEventsList(String userAuth) {
        TypedQuery<Event> query = em.createQuery
                ("select e from Event e join e.owner o where o.email=?1",
                        Event.class);
        query.setParameter(1,userAuth);
        return query.getResultList();
    }
    @Override
    public User getUserByAuth(String userAuth){
        TypedQuery<User> query =
                em.createQuery("select u from User u where u.email=?1",
                        User.class);
        query.setParameter(1,userAuth);
        return query.getSingleResult();
    }

    @Override
    public Event getEventById(long eventId) {
        Event event = em.find(Event.class,eventId);
        if(event == null){
            throw new NoSuchElementException();
        }
        return event;
    }

    @Override
    public User getUserById(int userId) {
        User user = em.find(User.class,userId);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;
    }

    @Override
    public Notification getNotificationById(int notificationId) {
        Notification notification = em.find(Notification.class,notificationId);
        if(notification == null){
            throw new IllegalArgumentException();
        }
        return notification;
    }

}



