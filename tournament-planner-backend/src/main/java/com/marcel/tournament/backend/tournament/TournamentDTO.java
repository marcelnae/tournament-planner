package com.marcel.tournament.backend.tournament;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * DTO class for Tournaments
 */
@Data
public class TournamentDTO {
    private Integer id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate; // Optional

}
