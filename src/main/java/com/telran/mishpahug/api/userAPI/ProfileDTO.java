package com.telran.mishpahug.api.userAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProfileDTO {


    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    private String confession;
    private String gender;
    private List<String> pictureLink;
    private String phoneNumber;
    private String maritalStatus;
    private List<String> foodPreferences;
    private List<String> language;
    private String description;
    private double rate;
    private int numberOfVoters;





}



