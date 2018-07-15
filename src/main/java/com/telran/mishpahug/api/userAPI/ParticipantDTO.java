package com.telran.mishpahug.api.userAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private int userId;
    private String fullName;
    private String confession;
    private String gender;
    private LocalDate dateOfBirth;
    private int age;
    private List<String> pictureLink;
    private String phoneNumber;
    private String maritalStatus;
    private List<String> foodPreferences;
    private List<String> language;
    private double rate;
    private int numberOfVoters;
    private boolean isInvited;


    public ParticipantDTO(int userId, String s, String confession, String gender,
                          LocalDate dateOfBirth,
                          List<String> pictureLink, String phoneNumber,
                          String maritalStatus, List<String> foodPreferences,
                          List<String> language, double rate, int numberOfVoters,
                          boolean isInvited) {
        this.userId = userId;
        this.fullName = s;
        this.confession = confession;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        LocalDate currentDay = LocalDate.now();
        this.age = (int) ChronoUnit.YEARS.between(dateOfBirth,currentDay);
        this.pictureLink = pictureLink;
        this.phoneNumber = phoneNumber;
        this.maritalStatus = maritalStatus;
        this.foodPreferences = foodPreferences;
        this.language = language;
        this.rate = rate;
        this.numberOfVoters = numberOfVoters;
        this.isInvited = isInvited;

    }


}


