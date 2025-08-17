package com.marcel.tournament.backend.location;

import lombok.Data;
import org.springframework.data.annotation.Id;

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
