package com.telran.mishpahug.service;

import com.telran.mishpahug.api.eventAPI.*;
import com.telran.mishpahug.api.noteAPI.IsReadDTO;
import com.telran.mishpahug.api.noteAPI.NotificationDTO;
import com.telran.mishpahug.api.userAPI.*;
import com.telran.mishpahug.model.Event;
import com.telran.mishpahug.model.User;

import java.util.List;

public interface InterfaceService {
    StaticFieldsDTO getStaticFields();
    CurrentUserDTO registrationUser(String auth);
    List<Event> getPagination(List<Event> events, int page, int size);
    ListEventsInProgressDTO getEventsInProgress(int page, int size,
                                                LocationFilterDTO locationFilterDTO);
    ListEventsInProgressDTO getEventsInProgress2(int page, int size,
                                                 LocationFilterDTO locationFilterDTO);
    LoginUserDTO loginUser(String auth);
    String addEvent(EventDTO eventDTO,String auth);
    ProfileResponseDTO updateProfile(ProfileDTO profileDTO,String auth);
    ProfileResponseDTO getUserProfile(String auth);
    List<CalendarEventsDTO> getEventsListCalendar(String auth,int month);
    MyEventDTO myEventInfo(long eventId,String auth);
    SubEventDTO getSubscribedEventInfo(long eventId,String auth);
    List<NotificationDTO> getNotificationsList(String auth);
    List<MyEventDTO> getMyEventsList(String auth);
    List<MyEventsHistoryDTO> getMyEventsHistory(String auth);
    ParticipationListDTO getParticipationList(String auth);
    String subscribeToEvent(long eventId,String auth);
    String unSubscribeFromEvent(long eventId,String auth);
    String voteForEvent(long eventId,double voteCount,String auth);
    InviteDTO inviteToEventService(long eventId, int userId, String userAuth);
    ChangeStatusDTO changeEventStatus(long eventId,String auth);
    IsReadDTO notificationIsRead(int notificationId,String auth);
    String removeEvent(long eventId);
}
