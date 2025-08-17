package com.marcel.tournament.backend.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TournamentDTO {
    private Integer id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate; // Optional
}
