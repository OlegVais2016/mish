package com.telran.mishpahug.api.noteAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Setter
@Getter
public class IsReadDTO {
    private int code;
    private String message;

    public IsReadDTO(String message) {
        this.code = 200;
        this.message = "Notification is updated!";
    }



}
