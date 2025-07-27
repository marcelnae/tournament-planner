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

}
