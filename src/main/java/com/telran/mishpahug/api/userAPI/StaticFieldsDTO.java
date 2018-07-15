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
public class StaticFieldsDTO {
    private List<String> confession;
    private List<String> gender;
    private List<String> maritalStatus;
    private List<String> foodPreference;
    private List<String> language;
    private List<String> holiday;


}

