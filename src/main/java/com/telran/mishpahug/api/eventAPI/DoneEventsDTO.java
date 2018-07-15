package com.telran.mishpahug.api.eventAPI;

import com.telran.mishpahug.api.userAPI.OwnerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DoneEventsDTO {
    private long eventId;
    private String title;
    private String holiday;
    private String confession;
    private LocalDate date;
    private String description;
    private String status;
    private OwnerDTO owner;
}
