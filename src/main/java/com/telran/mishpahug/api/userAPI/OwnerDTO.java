package com.telran.mishpahug.api.userAPI;

import com.telran.mishpahug.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {

    private String fullName;
    private String confession;
    private String gender;
    private int age;
    private List<String> pictureLink;
    private String maritalStatus;
    private List<String> foodPreferences;
    private List<String> language;
    private double rate;
    private int numberOfVoters;


}
