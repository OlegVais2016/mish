package com.telran.mishpahug.service;

import com.telran.mishpahug.dao.InterfaceMishpahug;
import com.telran.mishpahug.model.Event;
import com.telran.mishpahug.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Autowired
    InterfaceMishpahug mishpahugOrm;

    @Autowired
    JavaMailSender emailSender;

   // @Scheduled(fixedDelay = 10*1000)
   @Scheduled(cron = "0 31 14 * * ?")

    public void changeToDone(){
        System.out.println("Start!");
        LocalDate currentDay = LocalDate.now();
        List<Event> events = mishpahugOrm.getAllEvents();
        for(Event event : events)
            if(!"done".equals(event.getStatus())
                    && event.getDate().isBefore(currentDay)) {
                mishpahugOrm.changeStatusToDone(event);

                /*List<User> users = event.getParticipants();
                for (User user : users){
                    if(user.getInvitedEventsId().equals(event.getEventId())){
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(user.getEmail());
                        message.setSubject(event.getTitle() + " is finished.");
                        message.setText("Please vote for this event");
                        emailSender.send(message);
                    }
                }*/
            }

    }

}
