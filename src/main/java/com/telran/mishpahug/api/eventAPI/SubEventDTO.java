package com.telran.mishpahug.api.eventAPI;

import com.telran.mishpahug.api.userAPI.OwnerDTO;
import com.telran.mishpahug.api.userAPI.ParticipantDTO;
import com.telran.mishpahug.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubEventDTO {
    private long eventId;
    private String title;
    private String holiday;
    private String confession;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private AddressDTO address;
    private List<String> kitchen;
    private String description;
    private String status;
    private OwnerDTO owner;
}
