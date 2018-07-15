package com.telran.mishpahug.api.eventAPI;

import com.telran.mishpahug.model.Address;
import com.telran.mishpahug.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocationFilterDTO {
    private Location location;
    private FilterDTO filters;

    }


