package com.telran.mishpahug.api.eventAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CalendarEventsDTO {

    private long eventID;
    private LocalDate date;
    private String status;


}


