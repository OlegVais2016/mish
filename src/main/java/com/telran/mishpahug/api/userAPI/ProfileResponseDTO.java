package com.telran.mishpahug.api.userAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@NoArgsConstructor

@Setter
@Getter
public class ProfileResponseDTO {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int age;
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


    public ProfileResponseDTO(String firstName, String lastName, LocalDate dateOfBirth,
                              String confession, String gender, List<String> pictureLink,
                              String phoneNumber, String maritalStatus,
                              List<String> foodPreferences, List<String> language,
                              String description, double rate,int numberOfVoters) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        LocalDate currentDay = LocalDate.now();
        this.age = (int) ChronoUnit.YEARS.between(dateOfBirth,currentDay);
        this.confession = confession;
        this.gender = gender;
        this.pictureLink = pictureLink;
        this.phoneNumber = phoneNumber;
        this.maritalStatus = maritalStatus;
        this.foodPreferences = foodPreferences;
        this.language = language;
        this.description = description;
        this.rate = rate;
        this.numberOfVoters = numberOfVoters;
    }
}
