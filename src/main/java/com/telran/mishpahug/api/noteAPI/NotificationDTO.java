package com.telran.mishpahug.api.noteAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotificationDTO {
    private int notificationId;
    private String title;
    private String message;
    private LocalDate date;
    private String type;
    private boolean isRead;
}
