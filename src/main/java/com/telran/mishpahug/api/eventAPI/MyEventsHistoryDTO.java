package com.telran.mishpahug.api.eventAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyEventsHistoryDTO {
    private long eventId;
    private String title;
    private String holiday;
    private String confession;
    private LocalDate date;
    private int duration;
    private List<String> kitchen;
    private String description;
    private String status;

   }
