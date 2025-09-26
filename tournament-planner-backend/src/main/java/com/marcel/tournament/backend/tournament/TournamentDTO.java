package com.marcel.tournament.backend.tournament;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * DTO class for Tournaments
 */
@Data
public class TournamentDTO {
    private Long id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate; // Optional

    public Tournament toEntity() {
        Tournament tournament = new Tournament();
        tournament.setId(id);
        tournament.setName(name);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        return tournament;
    }

}
