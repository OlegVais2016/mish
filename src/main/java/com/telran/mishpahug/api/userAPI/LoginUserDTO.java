package com.telran.mishpahug.api.userAPI;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginUserDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String maritalStatus;
    private String confession;
    private List<String> pictureLink;
    private String phoneNumber;
    private List<String> foodPreferences;
    private List<String> language;
    private String description;
    private double rate;
    private int numberOfVoters;














}
