package com.marcel.tournament.backend.tournament;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * DTO class for Tournaments
 */
@Data
public class TournamentDTO {
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime endDate; // optional


    public Tournament toEntity() {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        return tournament;
    }

}
