package com.telran.mishpahug.controller;

import com.telran.mishpahug.api.eventAPI.*;
import com.telran.mishpahug.api.noteAPI.IsReadDTO;
import com.telran.mishpahug.api.noteAPI.NotificationDTO;
import com.telran.mishpahug.api.userAPI.*;
import com.telran.mishpahug.dao.InterfaceMishpahug;
import com.telran.mishpahug.dao.MishpahugOrm;
import com.telran.mishpahug.model.Event;
import com.telran.mishpahug.model.Notification;
import com.telran.mishpahug.model.User;
import com.telran.mishpahug.service.InterfaceService;
import com.telran.mishpahug.service.MishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class RequestHandler {

    @Autowired
    InterfaceService interfaceService;

    @RequestMapping(value = "/staticfields",method = RequestMethod.GET)
    public StaticFieldsDTO staticFields(){
        return interfaceService.getStaticFields();
    }

    @RequestMapping(value = "/event/allprogresslist",
    method = RequestMethod.POST)
    public ListEventsInProgressDTO getEventsInProgress(@RequestParam int page,
    @RequestParam int size, @RequestBody LocationFilterDTO locationFilterDTO){
        return interfaceService.getEventsInProgress(page,size,locationFilterDTO);
    }


    // Second method

    @RequestMapping(value = "/event/allprogresslist2",
            method = RequestMethod.POST)
    public ListEventsInProgressDTO getEventsInProgress2
            (@RequestParam int page,@RequestParam int size,
             @RequestBody LocationFilterDTO locationFilterDTO){
        return interfaceService.getEventsInProgress2(page,size,locationFilterDTO);
    }

    @RequestMapping(value = "/user/registration",method = RequestMethod.POST)
    public CurrentUserDTO registrationUser
            (@RequestHeader(value="Authorization") String auth){
        return interfaceService.registrationUser(auth);
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public LoginUserDTO loginUser(@RequestHeader(value="Authorization") String auth){
        return interfaceService.loginUser(auth);
    }

    @RequestMapping(value = "/event/creation", method = RequestMethod.POST)
    public String addEvent(@RequestBody EventDTO eventDTO,
                            @RequestHeader(value="Authorization") String auth) {
        return interfaceService.addEvent(eventDTO,auth);
    }

    @RequestMapping(value = "/user/profile",method = RequestMethod.POST)
    public ProfileResponseDTO updateProfile(@RequestBody ProfileDTO profileDTO,
            @RequestHeader(value="Authorization") String auth){
        return interfaceService.updateProfile(profileDTO,auth);
    }

    @RequestMapping(value = "/user/profile",method = RequestMethod.GET)
    public ProfileResponseDTO getUserProfile
            (@RequestHeader (value = "Authorization") String auth){
        return interfaceService.getUserProfile(auth);
    }

    @RequestMapping(value = "/event/calendar/{month}",method = RequestMethod.GET)
    public List<CalendarEventsDTO> getEventsListCalendar
            (@RequestHeader (value = "Authorization") String auth,
             @PathVariable int month) {
       return interfaceService.getEventsListCalendar(auth,month);
    }

    @RequestMapping(value = "/event/own/{eventId}",method = RequestMethod.GET)
    public MyEventDTO myEventInfo(@PathVariable long eventId,
                    @RequestHeader (value = "Authorization") String auth){
        return interfaceService.myEventInfo(eventId,auth);
    }

    @RequestMapping(value = "/event/subscribed/{eventId}",method = RequestMethod.GET)
    public SubEventDTO getSubscribedEventInfo(@PathVariable long eventId,
             @RequestHeader (value = "Authorization") String auth){
        return interfaceService.getSubscribedEventInfo(eventId,auth);
    }

    @RequestMapping(value = "/notification/list",method = RequestMethod.GET)
    public List<NotificationDTO> getNotificationsList
            (@RequestHeader (value = "Authorization") String auth){
        return interfaceService.getNotificationsList(auth);
    }

    @RequestMapping(value = "/event/currentlist",method = RequestMethod.GET)
    public List<MyEventDTO> getMyEventsList
            (@RequestHeader (value = "Authorization") String auth){
        return interfaceService.getMyEventsList(auth);
    }

    @RequestMapping(value = "/event/historylist",method = RequestMethod.GET)
    public List<MyEventsHistoryDTO> getMyEventsHistory
            (@RequestHeader (value = "Authorization") String auth){
        return interfaceService.getMyEventsHistory(auth);
    }

    @RequestMapping(value = "/event/participationlist",method = RequestMethod.GET)
    public ParticipationListDTO getParticipationList
            (@RequestHeader (value = "Authorization") String auth){
        return interfaceService.getParticipationList(auth);
    }

    @RequestMapping(value = "/event/subscription/{eventId}",method = RequestMethod.PUT)
    public String subscribeToEvent(@PathVariable long eventId,
                             @RequestHeader(value="Authorization") String auth ){
        return interfaceService.subscribeToEvent(eventId,auth);
    }

    @RequestMapping(value = "/event/unsubscription/{eventId}",
            method = RequestMethod.PUT)
    public String unSubscribeFromEvent
            (@PathVariable long eventId,
             @RequestHeader(value="Authorization") String auth ){
        return interfaceService.unSubscribeFromEvent(eventId,auth);
    }

    @RequestMapping(value = "/event/vote/{eventId}/{voteCount}",
            method = RequestMethod.PUT)
    public String voteForEvent
            (@PathVariable long eventId,@PathVariable double voteCount,
             @RequestHeader(value="Authorization") String auth ){
        return interfaceService.voteForEvent(eventId,voteCount,auth);
    }
    @RequestMapping(value = "/event/invitation/{eventId}/{userId}",
            method = RequestMethod.PUT)
    public InviteDTO inviteToEvent(
            @PathVariable long eventId,@PathVariable int userId,
            @RequestHeader(value="Authorization") String auth ){
       return interfaceService.inviteToEventService(eventId,userId,auth);
    }

    @RequestMapping(value = "/event/pending/{eventId}",method = RequestMethod.PUT)
    public ChangeStatusDTO changeEventStatus
            (@PathVariable long eventId,
             @RequestHeader(value="Authorization") String auth ){
        return interfaceService.changeEventStatus(eventId,auth);
    }

    @RequestMapping(value = "/notification/isRead/{notificationId}",
                    method = RequestMethod.PUT)
    public IsReadDTO notificationIsRead
            (@PathVariable int notificationId,
                    @RequestHeader(value="Authorization") String auth ){
        return interfaceService.notificationIsRead(notificationId,auth);
    }

    @RequestMapping(value = "/event/remove",method = RequestMethod.DELETE)
    public String removeEvent(@RequestParam long eventId){
        return interfaceService.removeEvent(eventId);
    }

}









