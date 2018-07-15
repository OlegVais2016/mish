package com.telran.mishpahug.model;

import com.telran.mishpahug.api.eventAPI.SubEventDTO;
import com.telran.mishpahug.api.userAPI.OwnerDTO;
import com.telran.mishpahug.api.userAPI.OwnerEventInProgressDTO;
import com.telran.mishpahug.api.userAPI.ParticipantDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable{

	@Id
	@GeneratedValue
	private long eventId;
	private String title;
	private String holiday;
	@ManyToOne
	private User owner;
//	@ElementCollection
	private String confession;
	private LocalDate date;
	private LocalTime time;
	private int duration;
	@ElementCollection
	private List<String> kitchen;
	private String description;
	private String status;			 //in_progress, pending, done
	private Address address;
	@ManyToMany(mappedBy = "requestedEvents")
	private List<User> participants;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event event = (Event) o;
		return eventId == event.eventId;
	}

	@Override
	public int hashCode() {

		return Objects.hash(eventId);
	}

	public Event(String title, String holiday, Address address, String confession,
				 LocalDate date, LocalTime time, int duration, List<String> kitchen,
				 String description) {
		this.title = title;
		this.holiday = holiday;
		this.address = address;
		this.confession = confession;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.kitchen = kitchen;
		this.description = description;
		this.status = "in progress";
	}

	public List<ParticipantDTO> getParticipantsDTO(List<User> users){

		List<ParticipantDTO> result = new ArrayList<>();
		for(User user : users) {
			ParticipantDTO participantDTO = new ParticipantDTO(user.getUserId(),
					user.getFirstName()+ " " + user.getLastName(),
					user.getConfession(), user.getGender(),user.getDateOfBirth(),
							user.getPictureLink(),
					user.getPhoneNumber(), user.getMaritalStatus(),
					user.getFoodPreferences(), user.getLanguage(),
					user.getRate(),user.getNumberOfVoters(), user.isInvited());
			result.add(participantDTO);
		}
		return result;
	}


	public OwnerDTO getOwnerDTO(User owner) {
		OwnerDTO ownerDTO = new OwnerDTO
				(owner.getFirstName() + " " +owner.getLastName(),
				owner.getConfession(),owner.getGender(),owner.getAge(),
						owner.getPictureLink(),owner.getMaritalStatus(),
						owner.getFoodPreferences(),owner.getLanguage(),
						owner.getRate(),owner.getNumberOfVoters());
		return ownerDTO;
	}
	public OwnerEventInProgressDTO getOwnerEventInProgressDTO(User owner) {
		OwnerEventInProgressDTO ownerEventInProgressDTO = new OwnerEventInProgressDTO
				(owner.getFirstName() + " " + owner.getLastName(),
						owner.getConfession(),owner.getGender(),owner.getAge(),
						owner.getPictureLink(),owner.getMaritalStatus(),
						owner.getFoodPreferences(),owner.getLanguage(),
						owner.getRate());
		return ownerEventInProgressDTO;
	}

}
