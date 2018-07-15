package com.telran.mishpahug.api.eventAPI;

import com.telran.mishpahug.api.userAPI.ParticipantDTO;
import com.telran.mishpahug.model.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyEventDTO {
    private long eventId;
    private String title;
    private String holiday;
    private String confession;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private List<String> kitchen;
    private String description;
    private String status;
    private List<ParticipantDTO> participants;



}



