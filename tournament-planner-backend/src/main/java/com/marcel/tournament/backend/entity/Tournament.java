package com.marcel.tournament.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tournament {

    @Id
    private Integer id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate; // Optional
}
