package com.telran.mishpahug.service;

import com.telran.mishpahug.api.eventAPI.*;
import com.telran.mishpahug.api.noteAPI.IsReadDTO;
import com.telran.mishpahug.api.noteAPI.NotificationDTO;
import com.telran.mishpahug.api.userAPI.*;
import com.telran.mishpahug.dao.InterfaceMishpahug;
import com.telran.mishpahug.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;
import javax.persistence.EntityExistsException;
import java.util.*;

@Service
public class MishService implements InterfaceService{

    @Autowired
    InterfaceMishpahug mishpahugOrm;

    @Autowired
    JavaMailSender emailSender;

    @Override
    public StaticFieldsDTO getStaticFields() {
        List<String> confession = new ArrayList<>();
        confession.add("religious");
        confession.add("not religious");
        List<String> gender = new ArrayList<>();
        gender.add("male");
        gender.add("female");
        List<String> maritalStatus = new ArrayList<>();
        maritalStatus.add("married");
        maritalStatus.add("single");
        maritalStatus.add("divorced");
        List<String> foodPreference = new ArrayList<>();
        foodPreference.add("vegetarian");
        foodPreference.add("kosher");
        foodPreference.add("non-vegetarian");
        List<String> language = new ArrayList<>();
        language.add("Hebrew");
        language.add("English");
        language.add("Russian");
        List<String> holiday = new ArrayList<>();
        holiday.add("Pesaсh");
        holiday.add("Shabbat");
        holiday.add("Others");
        StaticFieldsDTO staticFieldsDTO = new StaticFieldsDTO(confession,gender,
                maritalStatus,foodPreference,language,holiday);
        return staticFieldsDTO;
    }

    @Override
    public CurrentUserDTO registrationUser(String auth) {
        String sub = auth.substring(6);
        byte[] res = Base64.getDecoder().decode(sub);
        String credential = new String(res);
        String [] userAuth = credential.split(":",2);
        String email = userAuth[0];
        String password = userAuth[1];
        User user =  mishpahugOrm.registrationUser(email,password);
        return toRegisrate(user);
    }

    @Override
    public List<Event> getPagination(List<Event> events, int page, int size) {
        int totalEvents = events.size();
        int from = size *(page-1);
        from = from < 0 ? 0 : from;
        int to = size * page;
        to = to >= totalEvents ? totalEvents  : to;
        List<Event> res = events.subList(from,to);
        return res;
    }

    @Override
    public ListEventsInProgressDTO getEventsInProgress(int page, int size,
                                           LocationFilterDTO locationFilterDTO) {
        List<Event> events = mishpahugOrm.getAllEvents();
        List<Event> eventsProgress = new ArrayList<>();
        double xo = locationFilterDTO.getLocation().getLat();
        double yo = locationFilterDTO.getLocation().getLng();
        double r = locationFilterDTO.getLocation().getRadius();
        for (Event event : events) {
            double x = event.getAddress().getLocation().getLat();
            double y = event.getAddress().getLocation().getLng();
            if ( r*r < ((x-xo)*(x-xo)) + ((y-yo)*(y-yo))
                    && event.equals(locationFilterDTO.getFilters().getDateFrom())
                    && event.equals(locationFilterDTO.getFilters().getDateTo())
                    && event.equals(locationFilterDTO.getFilters().getHolidays())
                    && event.equals(locationFilterDTO.getFilters().getConfession())
                    && event.equals(locationFilterDTO.getFilters().getFood())) {
                eventsProgress.add(event);
            }
        }
        List<Event> eventsPagList = getPagination(eventsProgress,page,size);

        return toListEventsInProgressDTO(eventsPagList,page,size);
    }

    @Override
    public ListEventsInProgressDTO getEventsInProgress2(int page, int size,
                                          LocationFilterDTO locationFilterDTO) {
        // page - number of page, size - quantity events in this page
        return null;
    }

    @Override
    public LoginUserDTO loginUser(String auth) {
        String sub = auth.substring(6);
        byte[] res = Base64.getDecoder().decode(sub);
        String credential = new String(res);
        String [] userAuth = credential.split(":",2);
        String email = userAuth[0];
        String password = userAuth[1];
        User user = mishpahugOrm.loginUser(email,password);
        return toLoginUser(user);
    }

    @Override
    public String addEvent(EventDTO eventDTO, String auth) {
        String sub = auth.substring(6);
        byte[] res = Base64.getDecoder().decode(sub);
        String credential = new String(res);
        String [] userAuth = credential.split(":",2);
        String email = userAuth[0];
        String password = userAuth[1];
        Location location = new Location
                (eventDTO.getAddress().getLocation().getLat(),
                        eventDTO.getAddress().getLocation().getLng());
        Address address = new Address(eventDTO.getAddress().getCity(),
                eventDTO.getAddress().getPlace_id(),
                location);
        Event event = new Event(eventDTO.getTitle(),eventDTO.getHoliday(),
                address,eventDTO.getConfession(),eventDTO.getDate(),
                eventDTO.getTime(),eventDTO.getDuration(),eventDTO.getKitchen(),
                eventDTO.getDescription());
        return mishpahugOrm.addEvent(eventDTO,email,password,event);
    }

    @Override
    public ProfileResponseDTO updateProfile(ProfileDTO profileDTO, String auth) {
        String userAuth = decodeAuth(auth);
        User user = mishpahugOrm.updateProfile(profileDTO,userAuth);
        return toUserProfile(user);
    }

    @Override
    public ProfileResponseDTO getUserProfile(String auth) {
        String userAuth = decodeAuth(auth);
        User user = mishpahugOrm.getUserByAuth(userAuth);
        if (user == null) {
            throw new IllegalArgumentException(userAuth);
        }

        return toUserProfile(user);
    }

    @Override
    public List<CalendarEventsDTO> getEventsListCalendar(String auth, int month) {
        String userAuth = decodeAuth(auth);
        User user = mishpahugOrm.getUserByAuth(userAuth);
        List<Event> events = user.getRequestedEvents();
        List<Event> res = new ArrayList<>();
        for (Event event: events) {
            if(event.getDate().getMonthValue() == month
                    && (!event.getStatus().equals("done"))){
                res.add(event);
            }
        }
        List<Event> createdEvents = mishpahugOrm.getAllMyEventsList(userAuth);
        List<Event> res2 = new ArrayList<>();
        for (Event event : createdEvents){
            if(event.getDate().getMonthValue() == month
                    && (!event.getStatus().equals("done"))){
                res2.add(event);
            }
        }
        List<Event> result = new ArrayList<>(res.size() + res2.size());
        result.addAll(res);
        result.addAll(res2);

        return toListCalendarDTO(result);
    }

    private List<CalendarEventsDTO> toListCalendarDTO(List<Event> events) {
        List<CalendarEventsDTO> result = new ArrayList<>();
        for(Event event : events){
            CalendarEventsDTO calendarEventsDTO =
                    new CalendarEventsDTO(event.getEventId(),event.getDate(),
                            event.getStatus());
            result.add(calendarEventsDTO);
        }
        return result;
    }
    @Override
    public MyEventDTO myEventInfo(long eventId, String auth) {
        String userAuth = decodeAuth(auth);
        List<Event> events = mishpahugOrm.getAllMyEventsList(userAuth);
        for (Event event: events) {
            if(event.getEventId() == eventId){
                return  eventInfo(event);
            }
        }
        throw  new NoSuchElementException();
    }

    @Override
    public SubEventDTO getSubscribedEventInfo(long eventId, String auth) {
        String userAuth = decodeAuth(auth);
        User user = mishpahugOrm.getUserByAuth(userAuth);
        if (user == null) {
            throw new IllegalArgumentException(userAuth);
        }
        List<Event> events = user.getRequestedEvents();
        for (Event event : events){
            if(event.getEventId() == eventId){
                return toSubscribedEventInfo(event);
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<NotificationDTO> getNotificationsList(String auth) {
        String userAuth = decodeAuth(auth);
        User beneficiary = mishpahugOrm.getUserByAuth(userAuth);
        if (beneficiary == null) {
            throw new IllegalArgumentException(userAuth);
        }
        List<Notification> notifications = beneficiary.getNotifications();
        notifications.sort(new NotificationsComparator());
        return toNotificationsList(notifications);
    }

    @Override
    public List<MyEventDTO> getMyEventsList(String auth) {
        String userAuth = decodeAuth(auth);
        List<Event> events = mishpahugOrm.getAllMyEventsList(userAuth);
        List<Event> myCurrentEvents = new ArrayList<>();
        for(Event event : events){
            if(event.getStatus().equals("in progress") ||
                    event.getStatus().equals("pending")){
                myCurrentEvents.add(event);
            }
        }
        myCurrentEvents.sort(new DateComparator());
        return toEventsList(myCurrentEvents);
    }

    @Override
    public List<MyEventsHistoryDTO> getMyEventsHistory(String auth) {
        String userAuth = decodeAuth(auth);
        List<Event> events = mishpahugOrm.getAllMyEventsList(userAuth);
        List<Event> myHistoryEvents = new ArrayList<>();
        for(Event event : events){
            if(event.getStatus().equals("done")){
                myHistoryEvents.add(event);
            }
        }
        myHistoryEvents.sort(new DateComparator());

     //   Collections.sort(myHistoryEvents, new DateComparator());


        return toListEventsHistory(myHistoryEvents);
    }

    @Override
    public ParticipationListDTO getParticipationList(String auth) {
        String userAuth = decodeAuth(auth);
        User user = mishpahugOrm.getUserByAuth(userAuth);

        List<Event> events = user.getRequestedEvents();
        for(Event event : events){
            if(event.getStatus().equals("done")
                    && (event.getParticipants().contains(user.isVoted()))){
                events.remove(event);
            }
        }
        events.sort(new DateComparator());
        return toParticipationList(events);
    }

    @Override
    public String subscribeToEvent(long eventId, String auth) {
        String str;
        String userAuth = decodeAuth(auth);
        Event event = mishpahugOrm.getEventById(eventId);
        if(event == null) {
            return "No event";
        }
        User user = mishpahugOrm.getUserByAuth(userAuth);
        List<Event> events = user.getRequestedEvents();
        if (events.contains(event)) {
            return "User already subscribed";
        }else {
            mishpahugOrm.subscribeToEvent(events,event);
        }
        str = "User subscribed to the event by id: " + eventId + "!";
        return str;
    }

    @Override
    public String unSubscribeFromEvent(long eventId, String auth) {
        String str;
        String userAuth = decodeAuth(auth);
        Event event = mishpahugOrm.getEventById(eventId);
        if(event == null) {
            return "No event";
        }
        User user = mishpahugOrm.getUserByAuth(userAuth);
        List<Event> events = user.getRequestedEvents();
        if (!(events.contains(event))){
            return  "User was not subscribed";
        } else {
            mishpahugOrm.unSubscribeFromEvent(events,event);
        }
        str = "User unsubscribed from the event by id: " + eventId + "!";
        return str;
    }

    @Override
    public String voteForEvent(long eventId, double voteCount, String auth) {
        String userAuth = decodeAuth(auth);
        User user = mishpahugOrm.getUserByAuth(userAuth);
        if (user == null) {
            throw new IllegalArgumentException(userAuth);
        }
        user.setVoted(true);
        Event event = mishpahugOrm.getEventById(eventId);
        User owner = event.getOwner();
        int count = owner.getNumberOfVoters();
        double current = owner.getRate();
        current = (current*count + voteCount)/(count + 1);
        count++;
        mishpahugOrm.voteForeEvent(owner,count,current);
        String str = "Vote was accepted!";
        return str;
    }

    @Override
    public InviteDTO inviteToEventService(long eventId, int userId, String auth ){
        String userAuth = decodeAuth(auth);
        User owner = mishpahugOrm.getUserByAuth(userAuth);
        if (owner == null) {
            throw new IllegalArgumentException(userAuth);
        }
        Event event = mishpahugOrm.getEventById(eventId);
        if(!(event.getOwner().equals(owner))){
            throw new EntityExistsException();
        }
        User newParticipant = mishpahugOrm.getUserById(userId);
        mishpahugOrm.inviteToEvent(newParticipant,event);

        mishpahugOrm.createNotification(eventId,userId,userAuth);
        return toInviteToEvent(newParticipant);
    }

    @Override
    public ChangeStatusDTO changeEventStatus(long eventId, String auth) {
        String userAuth = decodeAuth(auth);
        User owner = mishpahugOrm.getUserByAuth(userAuth);
        if (owner == null) {
            throw new IllegalArgumentException(userAuth);
        }
        Event event = mishpahugOrm.getEventById(eventId);
        if(!owner.equals(event.getOwner())){
            throw new  EntityExistsException();
        }
        mishpahugOrm.changeEventStatus(event);
        event.getParticipants()
                .removeIf(u -> !u.getInvitedEventsId().contains(eventId));
        List<User> users = event.getParticipants();
        for(User user : users){
            String email = user.getEmail();
            String subject = "Invite to event " + event.getTitle();
            String text = "You are invited to address "
                    + event.getAddress().getCity() + " Date: "
                    + event.getDate() + " Place Id: "
                    + event.getAddress().getPlace_id() + " My name:  "
                    + event.getOwner().getFirstName() + " My tel: "
                    + event.getOwner().getPhoneNumber();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        }
        return toChangeEventStatus(event);
    }

    @Override
    public IsReadDTO notificationIsRead(int notificationId, String auth) {
        String userAuth = decodeAuth(auth);
        User beneficiary = mishpahugOrm.getUserByAuth(userAuth);
        if (beneficiary == null) {
            throw new IllegalArgumentException(userAuth);
        }
        Notification notification =
                mishpahugOrm.getNotificationById(notificationId);
        mishpahugOrm.notificationIsRead(beneficiary,notification);
        return toNotificationIsRead(notification);
    }

    @Override
    public String removeEvent(long eventId) {
        Event event = mishpahugOrm.getEventById(eventId);
        if(event == null){
            return "event not exist";
        }
        List<User> users = event.getParticipants();
        for (User user: users) {
            user.getRequestedEvents().remove(event);
            user.getInvitedEventsId().remove(event.getEventId());
        }
        mishpahugOrm.removeEvent(event);
        return "success";
    }

    private IsReadDTO toNotificationIsRead(Notification notification) {
        IsReadDTO isReadDTO = new IsReadDTO(notification.getMessage());
        return isReadDTO;
    }

    private ChangeStatusDTO toChangeEventStatus(Event event) {
        ChangeStatusDTO changeStatusDTO = new ChangeStatusDTO(event.getEventId(),
                event.getStatus());
        return changeStatusDTO;
    }

    private ParticipationListDTO toParticipationList(List<Event> events) {
        List<SubEventDTO> eventsInProgress = new ArrayList<>();
        List<SubEventDTO> progress = toGetSubEventDTO(events);
        for (SubEventDTO subEventDTO : progress) {
            if (subEventDTO.getStatus().equals("in progress")) {
                eventsInProgress.add(subEventDTO);
            }
        }
        List<SubEventDTO> eventsInPending = new ArrayList<>();
        List<SubEventDTO> pending = toGetSubEventDTO(events);
        for (SubEventDTO subEventDTO : pending) {
            if (subEventDTO.getStatus().equals("pending")) {
                eventsInPending.add(subEventDTO);
            }
        }
        List<DoneEventsDTO> eventsDone = new ArrayList<>();
        List<DoneEventsDTO> done = toGetDoneEventDTO(events);
        for (DoneEventsDTO doneEventsDTO : done) {
            if (doneEventsDTO.getStatus().equals("done")) {
                eventsDone.add(doneEventsDTO);
            }
        }
        ParticipationListDTO participationListDTO =
                new ParticipationListDTO(eventsInProgress,eventsInPending,eventsDone);
        return participationListDTO;
    }

    private List<SubEventDTO> toGetSubEventDTO(List<Event> events){
        List<SubEventDTO> res = new ArrayList<>();
        for (Event event : events){
            SubEventDTO subEventDTO = toSubscribedEventInfo(event);
            //method toSubscribedEventInfo is already done
            res.add(subEventDTO);
        }
        return res;
    }

    private List<DoneEventsDTO> toGetDoneEventDTO(List<Event>events){
        List<DoneEventsDTO> res = new ArrayList<>();
        for (Event event : events){
            User owner = event.getOwner();
            DoneEventsDTO doneEventsDTO = new DoneEventsDTO(event.getEventId(),
                    event.getTitle(),event.getHoliday(),event.getConfession(),
                    event.getDate(),event.getDescription(),event.getStatus(),
                    event.getOwnerDTO(owner));
            res.add(doneEventsDTO);
        }
        return res;
    }

    private List<MyEventsHistoryDTO> toListEventsHistory(List<Event> events) {
        List<MyEventsHistoryDTO> result = new ArrayList<>();
        for (Event event : events) {
            MyEventsHistoryDTO myEventsHistoryDTO =
                    new MyEventsHistoryDTO(event.getEventId(),event.getTitle(),
                            event.getHoliday(),event.getConfession(),event.getDate(),
                            event.getDuration(),event.getKitchen(),
                            event.getDescription(),event.getStatus());
            result.add(myEventsHistoryDTO);
        }
        return result;
    }
    private List<MyEventDTO> toEventsList(List<Event> events) {
        List<MyEventDTO> result = new ArrayList<>();
        for (Event event : events){
            MyEventDTO myEventDTO = new MyEventDTO(event.getEventId(),
                    event.getTitle(),event.getHoliday(),event.getConfession(),
                    event.getDate(),event.getTime(),event.getDuration(),
                    event.getKitchen(),event.getDescription(),event.getStatus(),
                    event.getParticipantsDTO(event.getParticipants()));
            result.add(myEventDTO);
        }
        return result;
    }
    private List<NotificationDTO> toNotificationsList
            (List<Notification> notifications) {
        List<NotificationDTO> result = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO =
                    new NotificationDTO(notification.getNotificationId(),
                            notification.getTitle(),notification.getMessage(),
                            notification.getDate(),notification.getType(),
                            notification.isRead());
            result.add(notificationDTO);
        }
        return result;
    }

    private SubEventDTO toSubscribedEventInfo(Event event) {
        AddressDTO addressDTO = new AddressDTO(event.getAddress().getCity());
        User owner = event.getOwner();
        SubEventDTO subEventDTO = new SubEventDTO(event.getEventId(),
                event.getTitle(),event.getHoliday(),event.getConfession(),
                event.getDate(),event.getTime(),event.getDuration(),
                addressDTO,event.getKitchen(),
                event.getDescription(),event.getStatus(),event.getOwnerDTO(owner));
        return subEventDTO;
    }

    private MyEventDTO eventInfo(Event event) {
        MyEventDTO myEventDTO = new MyEventDTO(event.getEventId(),
                event.getTitle(),event.getHoliday(),event.getConfession(),
                event.getDate(),event.getTime(),event.getDuration(),
                event.getKitchen(),event.getDescription(),event.getStatus(),
                event.getParticipantsDTO(event.getParticipants()));
        return myEventDTO;
    }

    private ProfileResponseDTO toUserProfile(User user) {
        ProfileResponseDTO profileResponseDTO =
                new ProfileResponseDTO(user.getFirstName(),
                        user.getLastName(),user.getDateOfBirth(),user.getConfession(),
                        user.getGender(),user.getPictureLink(),user.getPhoneNumber(),
                        user.getMaritalStatus(),user.getFoodPreferences(),user.getLanguage(),
                        user.getDescription(),user.getRate(),user.getNumberOfVoters());
        return profileResponseDTO;
    }

    private LoginUserDTO toLoginUser(User user){
        LoginUserDTO loginUserDTO = new LoginUserDTO(user.getFirstName(),
                user.getLastName(),user.getDateOfBirth(),user.getGender(),
                user.getMaritalStatus(),user.getConfession(),user.getPictureLink(),
                user.getPhoneNumber(),user.getFoodPreferences(),user.getLanguage(),
                user.getDescription(),user.getRate(),user.getNumberOfVoters());
        return loginUserDTO;
    }

    private ListEventsInProgressDTO toListEventsInProgressDTO
            (List<Event> eventsPagList, int page, int size) {
        List<EventsInProgressDTO> content = new ArrayList<>();
        for (Event event : eventsPagList){
            AddressDTO addressDTO = new AddressDTO(event.getAddress().getCity());
            User owner = event.getOwner();
            EventsInProgressDTO eventsInProgressDTO =
                    new EventsInProgressDTO(event.getEventId(),event.getTitle(),
                            event.getHoliday(),event.getConfession(),
                            event.getDate(),event.getTime(),event.getDuration(),
                            addressDTO,event.getKitchen(),
                            event.getDescription(),
                            event.getOwnerEventInProgressDTO(owner));
            content.add(eventsInProgressDTO);
        }
        int totalElements = content.size();
        int lastPageNumber = (int) (Math.ceil(totalElements / size));// need +1
        ListEventsInProgressDTO listEventsInProgressDTO =
                new ListEventsInProgressDTO(content,totalElements,lastPageNumber,
                        size,page,size,page == 1,
                        page == lastPageNumber,null);
        return listEventsInProgressDTO;
    }

    private CurrentUserDTO toRegisrate(User user) {
        CurrentUserDTO currentUserDTO = new CurrentUserDTO(user.getConfession(),
                user.getGender(),user.getMaritalStatus(),user.getFoodPreferences(),
                user.getLanguage(),user.getHoliday());
        return currentUserDTO;
    }

    private InviteDTO toInviteToEvent(User user) {
        InviteDTO inviteDTO = new InviteDTO(user.getUserId(),user.isInvited());
        return inviteDTO;
    }

    private String decodeAuth(String auth){
        String sub = auth.substring(6);
        byte[] res = Base64.getDecoder().decode(sub);
        String credential = new String(res);
        String userAuth = credential.split(":")[0];
        return userAuth;
    }



}
