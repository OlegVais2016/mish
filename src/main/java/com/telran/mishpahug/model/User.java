package com.telran.mishpahug.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class User implements Serializable {
	@Column(unique = true)
	private String email;
	private String password;
	@Id
	@GeneratedValue
	private int userId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	@ElementCollection
	private List<String> pictureLink;
	private String phoneNumber;
	private String confession;
	private String gender;
	private int age;
	private String maritalStatus;
	@ElementCollection
	private List<String> foodPreferences;
	@ElementCollection
	private List<String> language;
	private String description;
	private String holiday;
	private double rate;
	private int numberOfVoters;
	private boolean isInvited;
	@ManyToMany
	private List<Event> requestedEvents;
	@OneToMany
	private List<Notification> notifications;
	@ElementCollection
	private List<Long> invitedEventsId;
	boolean voted;



	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {

		return Objects.hash(email);
	}
}
