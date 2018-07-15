package com.telran.mishpahug.api.userAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CurrentUserDTO {
    private String confession;
    private String gender;
    private String maritalStatus;
    private List<String> foodPreference;
    private List<String> language;
    private String holiday;
}
