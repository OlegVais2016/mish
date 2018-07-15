package com.telran.mishpahug.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Notification implements Serializable {
    @Id
    @GeneratedValue
    private int notificationId;
    private String title;
    private String message;
    private LocalDate date;
    private String type;
    private boolean isRead;
    @ManyToOne
    private User owner;
    @ManyToOne
    private User beneficiary;
}

