package com.marcel.tournament.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Location {
    @Id
    private Integer id;

    private String city;
    private String venue;

    public LocationDTO toDto() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(this.id);
        locationDTO.setCity(this.city);
        locationDTO.setVenue(this.venue);

        return locationDTO;
    }

}
