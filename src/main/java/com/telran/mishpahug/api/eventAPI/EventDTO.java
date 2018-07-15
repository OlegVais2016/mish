package com.telran.mishpahug.api.eventAPI;

import com.telran.mishpahug.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.ElementCollection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EventDTO {


    private String title;
    private String holiday;
    private Address address;
    private String confession;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private List<String> kitchen;
    private String description;


}


