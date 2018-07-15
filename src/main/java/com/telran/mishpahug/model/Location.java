package com.telran.mishpahug.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class Location implements Serializable {

    private double lat;
    private double lng;
    private double radius;

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
